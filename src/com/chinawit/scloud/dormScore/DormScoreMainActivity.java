package com.chinawit.scloud.dormScore;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chinawit.scloud.LoginActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.CWeek;
import com.chinawit.scloud.bean.PrimaryScore;
import com.chinawit.scloud.bean.Term;
import com.chinawit.scloud.bean.Terms;
import com.http.HttpUtil;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormScoreMainActivity extends NavBarActivity{
	
	 private Spinner spnDormNum;
	 private EditText edtDormNum;
	 private Spinner spnWeek;
	 private Spinner spnCheckLevel;
	 private Spinner spnTerm;
	 private Spinner spnCheckType;
	 private TextView tvShowWeek;
	 private Terms terms;
	 private Term term;
	 private int week = 0;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_score_main);
		initNavBar("评比查询", "<返回", "高级");
		 spnDormNum = (Spinner) findViewById(R.id.dorm_score_main_spn_dormNum);
	     edtDormNum = (EditText) findViewById(R.id.dorm_score_main_edt_dormNum);
	     spnTerm = (Spinner) findViewById(R.id.dorm_score_main_spn_year);
	     spnWeek = (Spinner) findViewById(R.id.dorm_score_main_spn_week);
	     tvShowWeek = (TextView) findViewById(R.id.dorm_score_main_tv_showWeek);
	     spnCheckLevel = (Spinner) findViewById(R.id.dorm_score_main_spn_level);
	     spnCheckType =  (Spinner) findViewById(R.id.dorm_score_main_spn_type);
	     
	     
	     
	    /* SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
	     String date=sdf.format(new java.util.Date());
	     int t = Integer.valueOf(date);   
	     String years[] = new String[8];
		for (int n = 0,i=0; n <years.length; n=n+2,i++) {
				years[n] = (t-i) + "秋";
				years[n+1] = (t-i) + "春";
			}
	        ArrayAdapter<String> adpYear = new ArrayAdapter<String>(DormScoreMainActivity.this, android.R.layout.simple_spinner_item, years);
	        adpYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spnTerm.setAdapter(adpYear); */
	        GetCurrentTerm();//获取当前学期，填充spnTerm
	        getCurrentWeekTask();//获取当前周，填充spnWeek
            spnTerm.setOnItemSelectedListener(new OnItemSelectedListener() {
    			@Override
    			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    				if(spnTerm.getSelectedItemPosition()==0){
    					//getCurrentWeekTask();
    					 String[] weeks = new String[week+1];
    	                    weeks[0]="全部";
    	                    for (int n=1; n<weeks.length;n++) {
    	                        weeks[n] = "" + (week - n + 1);
    	                    }
    	                    ArrayAdapter<String> adpWeek = new ArrayAdapter<String>(DormScoreMainActivity.this, android.R.layout.simple_spinner_item, weeks);
    	                    adpWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	                    spnWeek.setAdapter(adpWeek);
    	                    spnWeek.setSelection(0);
    	            }
    				else{
    			
    	            	String[] weekss= new String[31];
    	            	weekss[0]="全部";
    	            	for(int n=1;n<=30;n++){
    	            	  weekss[n]=String.valueOf(n);
    	            	}
    	                ArrayAdapter<String> adpweek = new ArrayAdapter<String>(DormScoreMainActivity.this, android.R.layout.simple_spinner_item, weekss);
        	            adpweek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	            spnWeek.setAdapter(adpweek);
    	            }
    			
    			}
    			@Override
    			public void onNothingSelected(AdapterView<?> parent) {
    			}
    		});
            
	}

    /**
     * 提交
     */
    public void onSubmit(View view) {
        if (edtDormNum.getText().toString().equals("")) {
            new AlertDialog.Builder(DormScoreMainActivity.this)    
            .setTitle("提示")  
            .setMessage("请填写寝室号")  
            .setNegativeButton("确定", null)
            .show();
        } else {
            getExamScoreTask();
        }
    }
    
    /**
     * 获取当周信息任务
     */
    private void getCurrentWeekTask() {
       showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetCurrentWeek.ashx";
        HttpUtil.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormScoreMainActivity.this)    
                .setTitle("错误")  
                .setMessage("获取当前周网络访问失败！\n请检查网络并确保畅通后重试。")  
                .setPositiveButton("重试", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCurrentWeekTask(); //查询网络
                        
                    }
                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                CWeek cweek = new CWeek();
                
                week = 0;
                try {
                	cweek= CWeek.fromJson(content);
                    week = Integer.valueOf(cweek.getCurrentWeek());
                } catch(Exception e) {
                	
                	new AlertDialog.Builder(DormScoreMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定",new OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        finish();
	                    }
	                })
	                .show();
                	return;
                }
                if (week <= 0) {                   
                    new AlertDialog.Builder(DormScoreMainActivity.this)    
                    .setTitle("提示")  
                    .setMessage("当前时间无法查询寝室考核成绩。")
                    .setNegativeButton("确定", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
                    return;
                } else {
                    //初始化spnWeek
                    String[] weeks = new String[week+1];
                    weeks[0]="全部";
                    for (int n=1; n<weeks.length;n++) {
                        weeks[n] = "" + (week - n + 1);
                    }
                    ArrayAdapter<String> adpWeek = new ArrayAdapter<String>(DormScoreMainActivity.this, android.R.layout.simple_spinner_item, weeks);
                    adpWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnWeek.setAdapter(adpWeek);
                    spnWeek.setSelection(0);
                    StringBuffer tvshow = new StringBuffer();
                    tvshow.append("第"+week+"周");
                    tvShowWeek.setText(tvshow.toString());
                }
            }
            
        });
    }

    
    
    /**
     * 获取当前学期信息任务
     */
    private void GetCurrentTerm() {
       showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetCurrentTerm.ashx";
        HttpUtil.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormScoreMainActivity.this)    
                .setTitle("错误")  
                .setMessage("获取当前周网络访问失败！\n请检查网络并确保畅通后重试。")  
                .setPositiveButton("重试", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	GetCurrentTerm(); //查询网络
                        
                    }
                })
                .setNegativeButton("取消", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                terms = new Terms();
                try {
                	terms = Terms.fromJson(content);
                } catch(Exception e) {              	
                	new AlertDialog.Builder(DormScoreMainActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", new OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        finish();
	                    }
	                })
	                .show();
                	return;
                }  
                if(terms.getResultCode().equals("0")){
                	new AlertDialog.Builder(DormScoreMainActivity.this)    
    		        .setTitle("提示")
    		        .setMessage("当前学期设置有误，请联系管理员!")  
    		        .setPositiveButton("确定", new OnClickListener() {
    					@Override
    					public void onClick(DialogInterface dialog, int which) {
    						finish();
    					}
    				})
    		        .setCancelable(false)
    		        .show();
    				return; //跳出
                }else{
                	
                    //初始化term
                	 String[] years = new String[terms.getTermsCount()];
                	 for (int n=1,i=0; i<terms.getTermsCount();i++) {
                	    term = terms.getResult().get(i);
                	    if(term.getIsCurrennt().equals("1")){
                	    	years[0]=term.getTerm();
                	    }else{
                	    	years[n]=term.getTerm();
                	    	n++;
                	    }
                	   }
                    ArrayAdapter<String> adpYear = new ArrayAdapter<String>(DormScoreMainActivity.this, android.R.layout.simple_spinner_item, years);
         	        adpYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         	        spnTerm.setAdapter(adpYear);
         	        spnTerm.setSelection(0);         	        
                    }
            }
            
        });
    }

    /**
     * 获取考核信息
     */
    private void getExamScoreTask() {
        showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetCheckInfoByDormNumYearWeek.ashx";
        RequestParams params = new RequestParams();        
        params.put("DormNum", spnDormNum.getSelectedItem().toString() + "-" + edtDormNum.getText().toString());
        params.put("Term", spnTerm.getSelectedItem().toString());
        params.put("Week", spnWeek.getSelectedItem().toString());
        params.put("CheckClass",spnCheckLevel.getSelectedItem().toString());
        if(spnCheckType.getSelectedItem().toString().equals("普查")){
            params.put("CheckType","0");
        	
        }
        if(spnCheckType.getSelectedItem().toString().equals("复查")){
            params.put("CheckType","1");
        	
        }
        if(spnCheckType.getSelectedItem().toString().equals("抽查")){
            params.put("CheckType","2");
        	
        }
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormScoreMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
                return;
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                PrimaryScore primaryScore = null;
                try {
                	primaryScore = PrimaryScore.fromJson(content);
                } catch(Exception e) {
                    new AlertDialog.Builder(DormScoreMainActivity.this)    
                    .setTitle("错误")  
                    .setMessage("数据解析异常")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
                if (primaryScore.getResultCode().equals("0")) {
                    new AlertDialog.Builder(DormScoreMainActivity.this)    
                    .setTitle("提示")  
                    .setMessage("没有寝室考核成绩数据。\n可能的原因是：\n该寝室该周未进行检查无数据\n或者该寝室未使用。")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
               Intent intent = new Intent();
               intent.putExtra("josnScore", primaryScore.toJson());
               intent.putExtra("DormNum", spnDormNum.getSelectedItem().toString() + "-" + edtDormNum.getText().toString());
          	   intent.putExtra("Term",spnTerm.getSelectedItem().toString());
          	   intent.putExtra("Week",spnWeek.getSelectedItem().toString());
          	   intent.putExtra("CheckLevel",spnCheckLevel.getSelectedItem().toString());
          	   intent.putExtra("CheckType",spnCheckType.getSelectedItem().toString());
               intent.setClass(DormScoreMainActivity.this, DormScoreInfoActivity.class);
               startActivity(intent);
               
            }
            
        });
    	
    }
    
    /**
     * 右按钮监听函数
     */
    public void onNavBarRightButtonClick(View view) {
    	if (UserInfo.getUser() == null) { //需要登录
            new AlertDialog.Builder(DormScoreMainActivity.this)    
            .setTitle("提示")  
            .setMessage("该功能仅登录用户才能使用，您未登录。\n您确定要登录账户？")  
            .setPositiveButton("确定", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setClass(DormScoreMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            })
            .setNegativeButton("取消",null)
            .show();            
        }
        else if (Integer.valueOf(UserInfo.getUser().getPower())>0&&Integer.valueOf(UserInfo.getUser().getPower())!=6) { //验证权限-教师才能使用
            Intent intent = new Intent();
            intent.putExtra("Week",String.valueOf(week));
            intent.putExtra("Terms",terms.toJson());
            intent.setClass(this, DormScoreAdvancedActivity.class);
            startActivity(intent); //高级查询页面
        } else { //权限不足
            new AlertDialog.Builder(this)    
            .setTitle("提示")  
            .setMessage("您的权限不足，无法使用这个功能。\n请您与管理员取得联系以便获取更多的权限。")  
            .setPositiveButton("确定", null)
            .show();
        }
    }
    
    /**
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
        .setMessage("您确定要退出评比查询模块吗？")
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }
*/
}
