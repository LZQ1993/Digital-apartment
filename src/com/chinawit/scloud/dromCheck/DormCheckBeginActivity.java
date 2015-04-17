package com.chinawit.scloud.dromCheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chinawit.scloud.LoginActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.chinawit.scloud.dormPlate.CaptureActivity;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class DormCheckBeginActivity extends NavBarActivity {
    private Button btnTab01;
    private Button btnTab02;
    private Button btnTab03;
    private int spnDormNumber = 0;     //上次选择了楼号位置
    private boolean needCheck = false; //需要检查
    private String CheckType = "0";
    
    /** 
     * onCreate 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm_check_begin);
        initNavBar("获取考核寝室号", "<返回",null);
        initView();
     
    }
    
    /**
     * 初始化ViewPager
     */
    private void initView() {
      		final Button btnTab01 = (Button) findViewById(R.id.dorm_check_begin_btn_tab01);
      		final Button btnTab02 = (Button) findViewById(R.id.dorm_check_begin_btn_tab02);
      		final Button btnTab03 = (Button) findViewById(R.id.dorm_check_begin_btn_tab03);
      		btnTab01.setBackgroundColor(0xFF2784ea);
			btnTab01.setTextColor(0xFFFFFFFF);
			btnTab02.setBackgroundColor(0xFFFFFFFF);
			btnTab02.setTextColor(0xFF2784ea);   
			btnTab03.setBackgroundColor(0xFFFFFFFF);
			btnTab03.setTextColor(0xFF2784ea); 
      		btnTab01.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View v) {
      					btnTab01.setBackgroundColor(0xFF2784ea);
      					btnTab01.setTextColor(0xFFFFFFFF);
          				btnTab02.setBackgroundColor(0xFFFFFFFF);
          				btnTab02.setTextColor(0xFF2784ea);   
          				btnTab03.setBackgroundColor(0xFFFFFFFF);
          				btnTab03.setTextColor(0xFF2784ea); 
          				CheckType = "0";
          				
      			}
      		});
      		btnTab02.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View arg0) {
      				btnTab01.setBackgroundColor(0xFFFFFFFF);
      				btnTab01.setTextColor(0xFF2784ea);
      				btnTab02.setBackgroundColor(0xFF2784ea);
      				btnTab02.setTextColor(0xFFFFFFFF);
      				btnTab03.setBackgroundColor(0xFFFFFFFF);
      				btnTab03.setTextColor(0xFF2784ea);
      				CheckType = "1";
      			}
      		});
      		btnTab03.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View arg0) {
      				btnTab01.setBackgroundColor(0xFFFFFFFF);
      				btnTab01.setTextColor(0xFF2784ea);
      				btnTab02.setBackgroundColor(0xFFFFFFFF);
      				btnTab02.setTextColor(0xFF2784ea);
      				btnTab03.setBackgroundColor(0xFF2784ea);
      				btnTab03.setTextColor(0xFFFFFFFF);
      				CheckType = "2";
      			}
      		});
      		
      		
        }
  /*  *//**
     * 导航右键
     *//*
    @Override
    public void onNavBarLeftButtonClick(View view) {
    	 onExit();
    }
    
    *//**
     * 返回键
     *//*
    @Override
    public void onBackPressed() {
        onExit();
    }
    
    *//**
     * 退出
     *//*
    public void onExit() {
        new AlertDialog.Builder(this)
        .setTitle("提示：退出寝室考核")
        .setMessage("您确定要退出寝室考核模块吗？")
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }*/
    
    /**
     * 打开二维码
     */
    public void onQrcode(View view) {
    	if(0<Integer.valueOf(UserInfo.getUser().getPower())){
    		new AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage("您要打开二维码扫描界面吗？")
            .setPositiveButton("确定",new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(DormCheckBeginActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_DEFAULT);
                }
            })
            .setNegativeButton("取消", null)
            .show();
    	}else{
    		 new AlertDialog.Builder(DormCheckBeginActivity.this)    
             .setTitle("提示")  
             .setMessage("该功能模块，仅特定权限账户才能使用。您的权限不足！")  
             .setPositiveButton("确定",null)
             .show();
    		 return;
    	}
    	
    }
    
    
    /**
     * 为了得到传回的数据，必须在前面的Activity中（指MainActivity类）重写onActivityResult方法
     * 
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    /**
     * 获取二维码返回信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_DEFAULT) {
            if (resultCode == RESULT_OK) {
                setupNewRecord(data.getStringExtra("data"));
            } 
            else if (resultCode == RESULT_ERROR) {
                new AlertDialog.Builder(this)    
                .setTitle("错误")  
                .setMessage("二维码扫描有误，可能的原因是数据错误或者您扫描的不是标准的门牌二维码格式。\n您可以尝试重新扫描或者与管理员取得联系。")
                .setPositiveButton("确定",null)
                .show();
            }
        }
    }
    
    
    /**
     * 打开手动输入
     */
    public void onManual(View view) {
    	if(0<Integer.valueOf(UserInfo.getUser().getPower())){
    		 showDormNubmerInputDialog();
    	}else{
    		 new AlertDialog.Builder(DormCheckBeginActivity.this)    
             .setTitle("提示")  
             .setMessage("该功能模块，仅特定权限账户才能使用。您的权限不足！")  
             .setPositiveButton("确定",null)
             .show();
    		 return;
    	}
    	
       
    }
    
    /**
     * 显示门牌号码输入对话框
     */
    private void showDormNubmerInputDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_dorm_number_input, null);        
        final EditText edtDormNum = (EditText) view.findViewById(R.id.dialog_dorm_number_input_edt_dormNum);
        final Spinner spnDormNum = (Spinner) view.findViewById(R.id.dialog_dorm_number_input_spn_dormNum);        
        //对话框
        new AlertDialog.Builder(this)
        .setView(view)
        .setTitle("提示：输入寝室号码")
        .setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spnDormNumber = spnDormNum.getSelectedItemPosition(); //号码缓存
                if (edtDormNum.getText().toString().equals("")) { //判断是否为空
                    showDormNubmerNotNullDialog();
                } else {
                    setupNewRecord(spnDormNum.getSelectedItem().toString() + "-" + edtDormNum.getText().toString());
                }
            }
        })
        .setNegativeButton("取消", null)
        .setCancelable(false) //触摸不消失
        .show();
    }
    
    /**
     * 显示寝室号码不为空对话框
     */
    private void showDormNubmerNotNullDialog() {
        new AlertDialog.Builder(this)
        .setTitle("提示")
        .setMessage("寝室号码不能为空")
        .setNegativeButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDormNubmerInputDialog(); //重新调用寝室输入对话框
            }
        })
        .setCancelable(false)//触摸不消失
        .show();
    }

    /**
     * 新建记录
     */
    private void setupNewRecord(String dormNumber) {
        //验证是否为普充值
        if (!CheckType.equals("2")) {
            checkDormExamTask(dormNumber,CheckType);
        } else {
            getDormPlateByDormNum(dormNumber);
        	
        }
    }
    
    /**
     * 检验寝室是否被检查
     */
    private void checkDormExamTask(final String dormNumber,String CheckType) {
        showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetDormIsCheck.ashx";
        String  str = UserInfo.getUser().getId()+"";
        RequestParams params = new RequestParams();
        params.put("DormNum", dormNumber);
        params.put("UserID", str);
        params.put("CheckType",CheckType);   
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormCheckBeginActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败！\n寝室考核时，需要保持网络情况畅通。\n请您在检查网络情况后重试。" +error.toString())  
                .setPositiveButton("确定",null)
                .show();
            }

            
          //00-》本周该寝室普查完毕！
          //01-》本周该寝室复查完毕！
          //02-》寝室号有误！
          //11-》寝室录入有效
          //03-》无该寝室普查数据
            @Override
            public void onSuccess(String content) {
                if (content.equals("00")) {
                    dismissProgressDialog();
                    new AlertDialog.Builder(DormCheckBeginActivity.this)    
                    .setTitle("提示")  
                    .setMessage("本周该寝室普查完毕！")  
                   .setPositiveButton("确定",null)                
                   .show();
                    return;
                }
                if (content.equals("01")) {
                	 dismissProgressDialog();
                     new AlertDialog.Builder(DormCheckBeginActivity.this)    
                     .setTitle("提示")  
                     .setMessage("本周该寝室复查完毕！")  
                    .setPositiveButton("确定",null)                
                    .show();
                     return;
                }
                
                if (content.equals("11")) {
                	getDormPlateByDormNum(dormNumber);
               } 
                
                if (content.equals("02")) {
            	   dismissProgressDialog();
                   new AlertDialog.Builder(DormCheckBeginActivity.this)    
                   .setTitle("提示")  
                   .setMessage("寝室号有误！")  
                  .setPositiveButton("确定",null)                
                  .show();
                   return;
            } 
                if(content.equals("03")){
            	  dismissProgressDialog();
                   new AlertDialog.Builder(DormCheckBeginActivity.this)    
                   .setTitle("提示")  
                   .setMessage("无该寝室普查数据!")  
                  .setPositiveButton("确定",null)                
                  .show();
                   return;          	   
               }
            }
        });
    }
    
    /**
     * 通过寝室号获取门牌
     */
    private void getDormPlateByDormNum(String dormNumber) {
        showProgressDialog();        
        String url = NetworkInfo.getServiceUrl() + "GetDormPlateByDormNum.ashx";
        RequestParams params = new RequestParams();
        params.put("DormNum", dormNumber);
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormCheckBeginActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败！\n寝室考核时，需要保持网络情况畅通。\n请您在检查网络情况后重试。")  
                .setPositiveButton("确定",null)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                //解析数据
                DormInfo dorm = null;
                try {
                    dorm = DormInfo.fromJson(content);
                } catch (Exception e) {
                    new AlertDialog.Builder(DormCheckBeginActivity.this)    
                    .setTitle("错误")  
                    .setMessage("数据解析异常")  
                    .setPositiveButton("确定", null)
                    .show();
                    return;
                }
                //数据为空
                if (dorm == null || dorm.getRoomMateCount() == 0) {
                    new AlertDialog.Builder(DormCheckBeginActivity.this)    
                    .setTitle("提示")  
                    .setMessage("该寝室没有成员！")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
                //跳转
                Intent intent = getIntent();
                intent.putExtra("jsonDormPlate",dorm.toJson());
                intent.putExtra("CheckType",CheckType);
                intent.setClass(DormCheckBeginActivity.this,DormCheckNewRecordActivity.class);
                startActivity(intent);
            }
        });
    	
    }
}