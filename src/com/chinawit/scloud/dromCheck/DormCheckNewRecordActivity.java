package com.chinawit.scloud.dromCheck;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.DormCheckRecord;
import com.chinawit.scloud.bean.DormInfo;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 新建考核记录
 */
public class DormCheckNewRecordActivity extends NavBarActivity {

    /**
     * view01
     */
    private RadioGroup rg_ground;
    private RadioGroup rg_smell;
    private RadioGroup rg_doorWindows;
    private RadioGroup rg_rubbish;
    private RadioGroup rg_toilet;
    
    /**
     * view02
     */
    private RadioGroup rg_thing;
    private RadioGroup rg_smoke;
    private RadioGroup rg_bed;
    private RadioGroup rg_whole;
    private RadioGroup rg_attitude;
    
    /**
     * view03
     */
    private TextView tv_infoadd;
    private RadioGroup rg_irrgularity;
    private EditText edt_remarks;
    private EditText edt_iIIegal;

    /**
     * 其他
     */
    private DormInfo dorm;
    private DormCheckRecord record;
    private String CheckType;
    private boolean dataCheckInfo = false; 
    private boolean feg = false;
    private String[] mateAdds;
    private String[] IIIegalContent = {
    		"乱拉网线、电线",
    		"拆卸床铺",
    		"饲养宠物",
    		"违规电器",
    		"门口堆垃圾",
    		"其他"
    		
    };
    private boolean[] bedAdds;
    private boolean[] smokeAdds;
    private boolean[] IIIegalAdds;
    
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        Intent intent = getIntent();
        try {
           dorm = DormInfo.fromJson(intent.getStringExtra("jsonDormPlate"));
            CheckType = intent.getStringExtra("CheckType");
            record = new DormCheckRecord();
	   	    record.setDormNum(dorm.getDormNum());
	   	    record.setCheckType(CheckType);
	   	    record.setSubmiMemberID(UserInfo.getUser().getId()+"");
        } catch (Exception e) {
            new AlertDialog.Builder(DormCheckNewRecordActivity.this)    
            .setTitle("错误")  
            .setMessage("寝室数据解析失败，请重试！" + e.toString())  
            .setPositiveButton("确定", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setCancelable(false)
            .show();
            return;
        }      
        setContentView(R.layout.activity_dorm_check_new_record);
        initNavBar("寝室考核", "返回", "模板");
        initViewPager();
        //初始化床位缓冲
        mateAdds = new String[dorm.getRoomMateCount()];
        for (int n = 0; n < mateAdds.length; n++) {
         mateAdds[n] = dorm.getRoomMate().get(n).getStudentName();
        }
        
        bedAdds = new boolean[dorm.getRoomMateCount()];
        smokeAdds = new boolean[dorm.getRoomMateCount()];
        IIIegalAdds = new boolean[IIIegalContent.length];
    }
    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        ViewPager vp = (ViewPager) findViewById(R.id.dorm_exam_new_record_vp);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.activity_dorm_check_new_record_item_01, null);
        View view2 = inflater.inflate(R.layout.activity_dorm_check_new_record_item_02, null);
        View view3 = inflater.inflate(R.layout.activity_dorm_check_new_record_item_03, null);
        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        initView01(view1);
        initView02(view2);
        initView03(view3);
        vp.setAdapter(new PagerAdapter() {
            @Override  
            public Object instantiateItem(ViewGroup container, int position) {    
                container.addView(views.get(position));  
                return views.get(position);  
            }
            @Override  
            public void destroyItem(ViewGroup container, int position, Object object) {  
                container.removeView(views.get(position));  
            }
            //标题
            @Override
            public CharSequence getPageTitle(int position) {
                String[] titles = {
                        "1-考核项目",
                        "2-考核项目",
                        "3-相关备注"
                };
                return titles[position];
            }
            @Override
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }
            @Override
            public int getCount() {
                return views.size();
            }
        });
        vp.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != 2) return; //最后一页计算
                String strAdd = "";
                //计算附加备注-吸烟
                String strSmokeAdd = "";
                for (int n = 0; n< smokeAdds.length; n++) {
                    if (smokeAdds[n] == true) {
                    	strSmokeAdd += mateAdds[n];
                        if (n != smokeAdds.length - 1) {
                        	strSmokeAdd += "、";
                        }
                    }
                }
                if (!strSmokeAdd.equals("")) {
                    strAdd += "*【是否吸烟】吸烟的有：" + strSmokeAdd;
                }
                //计算附加备注-床铺摆放
                String strBedAdd = "";
                for (int n = 0; n< bedAdds.length; n++) {
                    if (bedAdds[n] == true) {
                        strBedAdd += mateAdds[n];
                        if (n != bedAdds.length - 1) {
                            strBedAdd += "、";
                        }
                    }
                }
                if (!strBedAdd.equals("")) {
                    if (!strSmokeAdd.equals("")) strAdd += "\n"; 
                    strAdd += "*【床铺】不合格的有：" + strBedAdd;
                }
                //最后填充
                if (!strAdd.equals("")) {
                    tv_infoadd.setText(strAdd);
                } else {
                    tv_infoadd.setText("无");
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        vp.setCurrentItem(0);
    }
    /**
     * 初始化布局1
     */
    private void initView01(View view) {
        rg_ground = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_ground);
        rg_smell = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_smell);
        rg_doorWindows = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_doorWindows);
        rg_rubbish = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_rubbish);
        rg_toilet = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_toilet);
    }
    
    /**
     * 初始化布局2
     */
    private void initView02(View view) {
        rg_thing = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_thing);
        rg_bed = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_bed);
        rg_smoke = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_smoke);
        SmokeRadioButtonListener smokeListener = new SmokeRadioButtonListener();
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_smoke_01))).setOnClickListener(smokeListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_smoke_02))).setOnClickListener(smokeListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_smoke_03))).setOnClickListener(smokeListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_smoke_04))).setOnClickListener(smokeListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_smoke_05))).setOnClickListener(smokeListener);
        BedRadioButtonListener bedListener = new BedRadioButtonListener();
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_bed_01))).setOnClickListener(bedListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_bed_02))).setOnClickListener(bedListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_bed_03))).setOnClickListener(bedListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rb_bed_04))).setOnClickListener(bedListener);
        //其他
        rg_whole = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_whole);
        rg_attitude = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_attitude);
    }
    
    /**
     * radioButton监听器
     */
    private class SmokeRadioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.dorm_exam_new_record_rb_smoke_05) { //满分
                for (int n=0; n<smokeAdds.length; n++) {
                	smokeAdds[n] = false;
                }
            } else {
            	new AlertDialog.Builder(DormCheckNewRecordActivity.this)
                .setTitle("请选择【是否吸烟】吸烟的有")
                .setMultiChoiceItems(mateAdds, smokeAdds, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean bool) {
                    	smokeAdds[position] = bool;
                    }
                })
                .setPositiveButton("确定", null)
                .show();
            }
            
        }
    }
    
    /**
     * radioButton监听器
     */
    private class BedRadioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.dorm_exam_new_record_rb_bed_04) { //满分
                for (int n=0; n<bedAdds.length; n++) {
                    bedAdds[n] = false;
                }
            } else {
                new AlertDialog.Builder(DormCheckNewRecordActivity.this)
                .setTitle("请选择不合格【床铺】学生姓名")
                .setMultiChoiceItems(mateAdds, bedAdds, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean bool) {
                        bedAdds[position] = bool;
                    }
                })
                .setPositiveButton("确定", null)
                .show();
            }
        }
    }
    
    /**
     * 初始化布局3
     */
    private void initView03(View view) {
        tv_infoadd = (TextView) view.findViewById(R.id.dorm_exam_new_record_tv_infoadd);
        rg_irrgularity = (RadioGroup) view.findViewById(R.id.dorm_exam_new_record_rg_iIIegal);
        IIIegalRadioButtonListener iIIegalListener = new IIIegalRadioButtonListener();
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rg_iIIegal_01))).setOnClickListener(iIIegalListener);
        ((RadioButton)(view.findViewById(R.id.dorm_exam_new_record_rg_iIIegal_02))).setOnClickListener(iIIegalListener);
        edt_remarks = (EditText) view.findViewById(R.id.dorm_exam_new_record_edt_remarks);
        edt_iIIegal = (EditText) view.findViewById(R.id.dorm_exam_new_record_edt_iIIegal); 
       
        
    }

    
    /**
     * radioButton监听器
     */
    private class IIIegalRadioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            String strIIIegal = "";
            if (id == R.id.dorm_exam_new_record_rg_iIIegal_01) { //
                for (int n=0; n<IIIegalAdds.length; n++) {
                	IIIegalAdds[n] = false;
                	edt_iIIegal.setText("");
                }
            	
            } else {
            	new AlertDialog.Builder(DormCheckNewRecordActivity.this)
                .setTitle("请选择违规项目有")
                .setMultiChoiceItems(IIIegalContent, IIIegalAdds, new OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean bool) {
                    	IIIegalAdds[position] = bool;
                    }
                })
                .setPositiveButton("确定", null)
               .show();
			            }
        }
    }
    /**
     * 返回键
     */
    @Override
    public void onNavBarLeftButtonClick(View view) {
        onExit();
    }
    
    /**
     * 返回键
     */
    @Override
    public void onBackPressed() {
        onExit();
    }

    /**
     * 退出提示
     */
    private void onExit() {
        new AlertDialog.Builder(this)    
        .setTitle("提示：")  
        .setMessage("您未提交当前记录，退出后信息将会丢失。\n您放弃当前记录的填写吗？")
        .setPositiveButton("放弃填写",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
    
    /**
     * 导航右键
     */
    @Override
    public void onNavBarRightButtonClick(View view) {
        new AlertDialog.Builder(DormCheckNewRecordActivity.this)
        .setTitle("请选择一种得分模板")
        .setItems(new String[] {"默认", "拒绝检查" , "满分寝室"}, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(which == 0) { //默认
                        rg_ground.check(R.id.dorm_exam_new_record_rb_ground_02);
                        rg_smell.check(R.id.dorm_exam_new_record_rb_smell_02);
                        rg_doorWindows.check(R.id.dorm_exam_new_record_rb_doorWindows_02);
                        rg_rubbish.check(R.id.dorm_exam_new_record_rb_rubbish_02);
                        rg_toilet.check(R.id.dorm_exam_new_record_rb_toilet_02);
                        rg_smoke.check(R.id.dorm_exam_new_record_rb_smoke_05);
                        rg_thing.check(R.id.dorm_exam_new_record_rb_thing_03);
                        rg_bed.check(R.id.dorm_exam_new_record_rb_bed_04);
                        rg_whole.check(R.id.dorm_exam_new_record_rb_whole_02);
                        rg_attitude.check(R.id.dorm_exam_new_record_rb_attitude_02);
                        edt_remarks.setText("");
                        dataCheckInfo =false ;
                        feg = false;
                        edt_iIIegal.setText("");
                        rg_irrgularity.check(R.id.dorm_exam_new_record_rg_iIIegal_01);
                
                } else if (which == 1) { //拒绝检查
                    rg_ground.check(R.id.dorm_exam_new_record_rb_ground_01);
                    rg_smell.check(R.id.dorm_exam_new_record_rb_smell_01);
                    rg_doorWindows.check(R.id.dorm_exam_new_record_rb_doorWindows_01);
                    rg_rubbish.check(R.id.dorm_exam_new_record_rb_rubbish_01);
                    rg_toilet.check(R.id.dorm_exam_new_record_rb_toilet_01);
                    rg_smoke.check(R.id.dorm_exam_new_record_rb_smoke_01);
                    rg_thing.check(R.id.dorm_exam_new_record_rb_thing_01);
                    rg_bed.check(R.id.dorm_exam_new_record_rb_bed_01);
                    rg_whole.check(R.id.dorm_exam_new_record_rb_whole_01);
                    rg_attitude.check(R.id.dorm_exam_new_record_rb_attitude_01);
                    rg_irrgularity.check(R.id.dorm_exam_new_record_rg_iIIegal_01);
                    dataCheckInfo = true;
                    edt_iIIegal.setText("");
                    feg = true;
                }
                else if (which == 2) { //满分寝室
                    rg_ground.check(R.id.dorm_exam_new_record_rb_ground_02);
                    rg_smell.check(R.id.dorm_exam_new_record_rb_smell_02);
                    rg_doorWindows.check(R.id.dorm_exam_new_record_rb_doorWindows_02);
                    rg_rubbish.check(R.id.dorm_exam_new_record_rb_rubbish_03);
                    rg_toilet.check(R.id.dorm_exam_new_record_rb_toilet_03);
                    rg_smoke.check(R.id.dorm_exam_new_record_rb_smoke_05);
                    rg_thing.check(R.id.dorm_exam_new_record_rb_thing_04);
                    rg_bed.check(R.id.dorm_exam_new_record_rb_bed_04);
                    rg_whole.check(R.id.dorm_exam_new_record_rb_whole_03);
                    rg_irrgularity.check(R.id.dorm_exam_new_record_rg_iIIegal_01);
                    rg_attitude.check(R.id.dorm_exam_new_record_rb_attitude_02);
                    dataCheckInfo = false;
                    feg = true;
                    edt_iIIegal.setText("");
                    edt_remarks.setText("*满分寝室");
                }
                //情况附加信息
                for (int n = 0; n< mateAdds.length; n++) {
                	smokeAdds[n] = false;
                    bedAdds[n] = false;
                }
                tv_infoadd.setText("无");
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
    

    /**
     * 文字分转化数字分
     */
    private int getValueFromRadioGroup(RadioGroup rg) {
        RadioButton rb = (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());
        String strPoint = rb.getText().toString();
        if(strPoint.equals("0分")) {
            return 0;
        }
        else if(strPoint.equals("5分")) {
            return 5;
        }
        else if(strPoint.equals("10分")) {
            return 10;
        }
        else if(strPoint.equals("15分")) {
            return 15;
        }
        else if(strPoint.equals("20分")) {
            return 20;
        }
        else {
            return 0;
        }
    }

    

    /**
     * 填充记录
     */
    private void fillUpRecore(){
        //填充记录	
        record.setGround(String.valueOf(getValueFromRadioGroup(rg_ground)));
        record.setSmell(String.valueOf(getValueFromRadioGroup(rg_smell)));
        record.setWindow(String.valueOf(getValueFromRadioGroup(rg_doorWindows)));
        record.setGarbage(String.valueOf(getValueFromRadioGroup(rg_rubbish)));
        record.setToilet(String.valueOf(getValueFromRadioGroup(rg_toilet)));
        record.setGoods(String.valueOf(getValueFromRadioGroup(rg_thing)));
        record.setWhole(String.valueOf(getValueFromRadioGroup(rg_whole)));
        record.setAttitude(String.valueOf(getValueFromRadioGroup(rg_attitude)));
        record.setSmoker(String.valueOf(getValueFromRadioGroup(rg_smoke)));  
        record.setBed(String.valueOf(getValueFromRadioGroup(rg_bed)));  
       
       //添加备注    
       RadioButton rb_IIIegal_ = (RadioButton) rg_irrgularity.findViewById(rg_irrgularity.getCheckedRadioButtonId());
       record.setIIIegal(rb_IIIegal_.getText().toString());
      if(feg==false){             
        String strIIIegal = "";
        for (int n = 0; n< IIIegalAdds.length; n++) {
            if (IIIegalAdds[n] == true) {
            	strIIIegal += IIIegalContent[n];
                if (n != IIIegalAdds.length - 1) {
                	strIIIegal += "、";
                }
            }
        }
        record.setIIIegalOtherContent(edt_iIIegal.getText().toString());
        record.setIIIegalContent(strIIIegal);   
        }
       else{
    	   record.setIIIegalContent("");
    	   record.setIIIegalOtherContent("");
       	
        }
      
      if(record.calculatePoint().equals("0")&& dataCheckInfo == true){
        	record.setDataType("1");
            edt_remarks.setText("*寝室拒检，未被检查");
            record.setReMark(edt_remarks.getText().toString());
        }else{
        	record.setDataType("0");
        	 //备注
            String strRemarks = "";
            if (!tv_infoadd.getText().toString().equals("无")) {
                strRemarks += tv_infoadd.getText().toString();
            }
            if (!edt_remarks.getText().toString().equals("")) {
                if (strRemarks.equals("")) {
                    strRemarks = edt_remarks.getText().toString();
                } else {
                    strRemarks += "\n*其他备注信息：" + edt_remarks.getText().toString();
                }
            }
            //填充
            record.setReMark(strRemarks);
        }
        
     
        
    }

    /**
     * 发送记录
     */
    public void onSubmit(View view) {
    	if(rg_irrgularity.getCheckedRadioButtonId()==R.id.dorm_exam_new_record_rg_iIIegal_02){
    		if(IIIegalAdds[5] == true&&edt_iIIegal.getText().toString().equals("")){
    			 new AlertDialog.Builder(this)    
                 .setTitle("提示")  
                 .setMessage("该寝室有其他违规行为，请您填写关于违规情况的相关说明。")
                 .setPositiveButton("确定", null)
                 .show();
                 return;
    		}
    		
    	}
        //提示对话框
        new AlertDialog.Builder(this)    
        .setTitle("提示")  
        .setMessage("请您仔细检查并确认当前记录填写无误。\n您确定要提交记录？")
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fillUpRecore();              //填充记录
                receiveDormExamByJsonTask(); //开始一个网络任务
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
    
    /**
     * 提交数据
     */
    public void receiveDormExamByJsonTask() {
        showProgressDialog();        
        String url = NetworkInfo.getServiceUrl() + "SetCheckInfoByJson.ashx";
        RequestParams params = new RequestParams();
        params.put("json", record.toJson());
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormCheckNewRecordActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败！\n寝室考核时，需要保持网络情况畅通。\n请您在检查网络情况后重试。"+ error.toString())  
                .setPositiveButton("确定",null)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                if (content.equals("1")) {
                    new AlertDialog.Builder(DormCheckNewRecordActivity.this)    
                    .setTitle("提示")  
                    .setMessage("记录上传成功！")  
                    .setPositiveButton("确定", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
                } else {
                    new AlertDialog.Builder(DormCheckNewRecordActivity.this)    
                    .setTitle("错误")  
                    .setMessage("数据解析异常" )  
                    .setPositiveButton("确定", null)
                    .show();
                }
            }
        });
    }


    
    
}
