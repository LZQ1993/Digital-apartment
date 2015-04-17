package com.chinawit.scloud.dormScore;

import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.SeniorScore;
import com.chinawit.scloud.bean.Term;
import com.chinawit.scloud.bean.Terms;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormScoreAdvancedActivity extends NavBarActivity {
    private Spinner spnWeek;
    private Spinner spnYear;
    private Spinner spnLevel;
    private Spinner spnType;
    private Spinner spnScoreLevel;
    private Spinner spnIsOfClasses;
    private Spinner spnGrade;
    private Spinner spnCollege;
    private Spinner spnMajor;
    private Spinner spnClass;
    private int week;
    private Terms terms;
    private Term term;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm_score_advanced);
        initNavBar("评比高级查询", "<返回", null);
        
        spnWeek = (Spinner) findViewById(R.id.dorm_score_spn_week);
        spnYear = (Spinner) findViewById(R.id.dorm_score_spn_year);
        spnLevel = (Spinner) findViewById(R.id.dorm_score_spn_level);
        spnType = (Spinner) findViewById(R.id.dorm_score_spn_type);
        spnScoreLevel = (Spinner) findViewById(R.id.dorm_score_spn_ScoreLevel);
        spnCollege = (Spinner) findViewById(R.id.dorm_score_spn_college);
        spnGrade = (Spinner) findViewById(R.id.dorm_score_spn_grade);
        spnMajor = (Spinner) findViewById(R.id.dorm_score_spn_major);
        spnClass = (Spinner) findViewById(R.id.dorm_score_spn_class);
        
        week = Integer.valueOf(getIntent().getStringExtra("Week"));
        terms = Terms.fromJson(getIntent().getStringExtra("Terms"));
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
        
        getClassByUserId();
        getCurrentWeekTask() ;        
         
	      ArrayAdapter<String> adpYear = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, years);
	      adpYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      spnYear.setAdapter(adpYear);
		 spnCollege.setEnabled(false);
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
	     String date=sdf.format(new java.util.Date());
	     int t = Integer.valueOf(date); 
	     String grade[] = new String[5];
	     grade[0]="未指定";
	     for (int n = 1; n <grade.length; n++) {
				grade[n] = (t-n)+"";
			}
		 ArrayAdapter<String> adpGrade = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, grade);
		 adpGrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spnGrade.setAdapter(adpGrade); 
		 spnGrade.setEnabled(false); //默认不可用
		 
		 
		 String strsClass[] = new String[]{"未指定"};
	     ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClass);
 		 adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		 spnClass.setAdapter(adpClass);
 		 
	     String strsMajor[] = new String[]{"未指定"};
	     ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsMajor);
 		 adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		 spnMajor.setAdapter(adpMajor);
 		 spnMajor.setEnabled(false);
 		 
 		spnYear.setOnItemSelectedListener(new OnItemSelectedListener() {
 			@Override
 			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
 				if(spnYear.getSelectedItemPosition()==0){
 				  // getCurrentWeekTask() ;
 				  String[] weeks = new String[week];
                  weeks[0]=""+week;
                 
                  ArrayAdapter<String> adpWeek = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, weeks);
                  adpWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  spnWeek.setAdapter(adpWeek);
                  spnWeek.setSelection(0);
 	            }
 				else{
 					String strsClass[] = new String[]{"未指定"};
              		ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClass);
            		 adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            		 spnClass.setAdapter(adpClass);
            		 String[] weekss= new String[30];
 	            	for(int n=0;n<30;n++){
 	            	  weekss[n]=String.valueOf(n+1);
 	            	}
 	                ArrayAdapter<String> adpweek = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, weekss);
     	            adpweek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     	            spnWeek.setAdapter(adpweek);
/* 					String strsMajor[] = new String[]{"未指定"};
             	    ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsMajor);
              		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              		spnMajor.setAdapter(adpMajor);*/
              		spnGrade.setSelection(0);
              		spnMajor.setSelection(0);

              		
              		spnClass.setSelection(0);
 	            }
 			
 			}
 			@Override
 			public void onNothingSelected(AdapterView<?> parent) {
 			}
 		});
 		
		
	       
 		spnIsOfClasses = (Spinner) findViewById(R.id.dorm_score_spn_user);
 		spnIsOfClasses.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                if (position == 0) { //复查下拉不可用
                	spnCollege.setEnabled(false);
                	spnCollege.setSelection(0);
                	spnGrade.setEnabled(false);
                	spnGrade.setSelection(0);
                	spnMajor.setEnabled(false);
                	spnMajor.setSelection(0);
                	spnClass.setEnabled(true);
                	getClassByUserId();
                } else { //恢复复查下拉
                	spnCollege.setEnabled(true);
                	spnGrade.setEnabled(true);
                	spnMajor.setEnabled(true);
                	String strsClass[] = new String[]{"未指定"};
            	    ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClass);
             		adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             		spnClass.setAdapter(adpClass);
             		spnClass.setEnabled(false);
             		spnClass.setSelection(0);
                	if (0<Integer.valueOf(UserInfo.getUser().getPower())&&Integer.valueOf(UserInfo.getUser().getPower())<6) {
                		if(UserInfo.getUser().getCollege().equals("电子与信息工程学院")){
                			spnCollege.setSelection(1);
                			
                		}
                		if(UserInfo.getUser().getCollege().equals("安全科学与工程学院")){
                			spnCollege.setSelection(2);
                			
                		}
                		if(UserInfo.getUser().getCollege().equals("电气与控制工程学院")){
                			spnCollege.setSelection(3);
                	
                		}
                		if(UserInfo.getUser().getCollege().equals("工商管理学院")){
                			spnCollege.setSelection(4);
                		
                		}
                		if(UserInfo.getUser().getCollege().equals("矿业技术学院")){
                			spnCollege.setSelection(5);
                			
                		}
                		if(UserInfo.getUser().getCollege().equals("营销学院")){
                			spnCollege.setSelection(6);                			
                		}
                		if(UserInfo.getUser().getCollege().equals("软件学院")){
                			spnCollege.setSelection(7);                			
                		}
                		spnCollege.setEnabled(false);
                	}
                	spnCollege.setOnItemSelectedListener(new OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                          if(spnCollege.getSelectedItemPosition()!=0){
                        	  getMajorByDepartmentTask();
                          }else{
                        	String strsMajor[] = new String[]{"未指定"};
                     	    ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsMajor);
                      		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      		spnMajor.setAdapter(adpMajor);
                      		spnGrade.setSelection(0);
                          }
                         }
                         @Override
                         public void onNothingSelected(AdapterView<?> adapter) {
                         }
                     });
                             	
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
 		
 		
 		 
 		spnMajor.setOnItemSelectedListener(new OnItemSelectedListener() {
 			@Override
 			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
 				if (spnMajor.getSelectedItemPosition() != 0) {
					
					getClassByConditionTask();
				}	
 			}
 			@Override
 			public void onNothingSelected(AdapterView<?> parent) {
 			}
 		});
  
    }
    /**
	 * 通过UserId获取所带班级
	 */
    public void getClassByUserId() {
    	//showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetClassByUserId.ashx";
		RequestParams params = new RequestParams();
		params.put("UserId", UserInfo.getUser().getId()+"");		
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable error) {
	               // dismissProgressDialog();
	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！"+error.toString())  
	                .setPositiveButton("确定",null)
	                .show();	        		
	        		return;
				}

				@Override
				public void onSuccess(String content) {
	               // dismissProgressDialog();
	                String[] tempsClassAbb = null;
	                try {
	                	tempsClassAbb = JsonUtil.fromJson(content, String[].class);
	                } catch (Exception e) {
	    	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
	    	                .setTitle("错误")  
	    	                .setMessage("服务器返回：" + content)  
	    	                .setPositiveButton("确定", null)
	    	                .show();    	           		        
	                	    return;
	                }
	    			//访问成功
	                String[] strsClassAbb = new String[tempsClassAbb.length + 1];
	                strsClassAbb[0] = "全部";
	                for (int n=0; n < tempsClassAbb.length; n++) {
	                	strsClassAbb[n+1] = tempsClassAbb[n];
	                }
	        		ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
	        		adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		spnClass.setAdapter(adpClass);
	        		
				}
			});	
	}

	/**
	 * 通过条件获取班级
	 */
	public void getClassByConditionTask() {	
		if(spnGrade.getSelectedItemId()==0){
			new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个学年!")  
            .setPositiveButton("确定", null)
            .show(); 
			spnMajor.setSelection(0);
    	    return;	
		}
		//showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetClassList.ashx";
		RequestParams params = new RequestParams();
		params.put("department", spnCollege.getSelectedItem().toString());
		params.put("Professional", spnMajor.getSelectedItem().toString());
		params.put("AcademicYear", spnGrade.getSelectedItem().toString());		
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
               // dismissProgressDialog();
            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！"+error.toString())  
                .setPositiveButton("确定",null)
                .show();
        		//清除班级
        		String[] strsClassAbb = new String[] {"未指定"};
        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);
        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnClass.setAdapter(adpClassAbb);
        		spnGrade.setSelection(0);            	
            	spnMajor.setSelection(0);
            	spnClass.setSelection(0);
        		return;
			}

			@Override
			public void onSuccess(String content) {
               // dismissProgressDialog();
                String[] tempsClassAbb = null;
                try {
                	tempsClassAbb = JsonUtil.fromJson(content, String[].class);
                } catch (Exception e) {
    	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
    	                .setTitle("错误")  
    	                .setMessage("服务器返回：" + content)  
    	                .setPositiveButton("确定", null)
    	                .show();    	           
	        		//清除班级
	        		String[] strsClassAbb = new String[] {"未指定"};
	        		ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);
	        		adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		spnClass.setAdapter(adpClass);
	        		spnGrade.setSelection(0);            	
	            	spnMajor.setSelection(0);
	            	spnClass.setSelection(0);
                	return;
                }
    			//访问成功
                String[] strsClassAbb = new String[tempsClassAbb.length + 1];
                strsClassAbb[0] = "全部";
                for (int n=0; n < tempsClassAbb.length; n++) {
                	strsClassAbb[n+1] = tempsClassAbb[n];
                }
        		ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
        		adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnClass.setAdapter(adpClass);
        		
			}
		});
	}
    /**
     * 获取当周信息任务
     */
    private void getCurrentWeekTask() {
    	//初始化spnWeek
        String[] weeks = new String[week];
        for (int n=0; n<weeks.length; n++) {
            weeks[n] = (week - n)+"";
        }
        ArrayAdapter<String> adpWeek = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, weeks);
        adpWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeek.setAdapter(adpWeek);
    }
    /**
	 * 通过学院获取专业
	 */
	public void getMajorByDepartmentTask() {
		//showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "GetProfessionalList.ashx";
		RequestParams params = new RequestParams();
		params.put("College", spnCollege.getSelectedItem().toString());		
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
               // dismissProgressDialog();
            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
            	//清除学院
            	spnGrade.setSelection(0);
            	spnGrade.setEnabled(false);
            	spnMajor.setSelection(0);
            	spnMajor.setEnabled(false);          	
            	spnCollege.setSelection(0);
            	spnCollege.setEnabled(false);
            	spnIsOfClasses.setSelection(0);
			}

			@Override
			public void onSuccess(String content) {
              //  dismissProgressDialog();
                String[] tempsMajor = null;
                try {
                	tempsMajor =JsonUtil.fromJson(content, String[].class);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
	                .setTitle("错误")  
	                .setMessage("服务器返回：" + content)  
	                .setPositiveButton("确定", null)
	                .show();
	            	//清除学院
	            	spnGrade.setSelection(0);
	            	spnGrade.setEnabled(false);
	            	spnMajor.setSelection(0);
	            	spnMajor.setEnabled(false);          	
	            	spnCollege.setSelection(0);
	            	spnCollege.setEnabled(false);
	            	spnIsOfClasses.setSelection(0);
                	return;
                }
    			//访问成功
                
                String[] strsMajor = new String[tempsMajor.length + 1];
                strsMajor[0] = "未指定";
                for (int n=0; n < tempsMajor.length; n++) {
                	strsMajor[n+1] = tempsMajor[n];
                }
        		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsMajor);
        		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnMajor.setAdapter(adpMajor);
        		spnClass.setEnabled(true);
        		String strsClass[] = new String[]{"未指定"};
        		ArrayAdapter<String> adpClass = new ArrayAdapter<String>(DormScoreAdvancedActivity.this, android.R.layout.simple_spinner_item, strsClass);
        		adpClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spnClass.setAdapter(adpClass);
			}

		});
	}
		
    /**
     * 提交
     */
    public void onSubmit(View view) {
    	if(spnIsOfClasses.getSelectedItemPosition() == 1&&(spnGrade.getSelectedItemPosition() == 0||spnMajor.getSelectedItemPosition() == 0)){
    		 new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
             .setTitle("提示")  
             .setMessage("您必须指定全部的查询条件")  
             .setPositiveButton("确定", null)
             .show();
        	}else{
        		//Toast.makeText(this, UserInfo.getUser().getId(), Toast.LENGTH_LONG).show();
        		getExamScoreAdvancedTask();
        	}
    	
    }
    
    /*
     * 通过条件查询
     */

    public void getExamScoreAdvancedTask(){
    	showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetCheckInfoByCondition.ashx";
        RequestParams params = new RequestParams();
        //params.put("UserId", UserInfo.getUser().getId()+"");
        params.put("IsOfClasses", spnIsOfClasses.getSelectedItem().toString());
        
        params.put("Week", spnWeek.getSelectedItem().toString());
        params.put("Term", spnYear.getSelectedItem().toString());
        params.put("CheckClass", spnLevel.getSelectedItem().toString());
        if(spnType.getSelectedItem().equals("普查")){
        	params.put("CheckType","0");
        }
        if(spnType.getSelectedItem().equals("复查")){
        	params.put("CheckType", "1");
        }
       if(spnType.getSelectedItem().equals("抽查")){
    	   params.put("CheckType", "2");
       }
	    params.put("ScoreLevel", spnScoreLevel.getSelectedItem().toString());	
		if(spnCollege.getSelectedItemPosition() == 0) params.put("College","");
		else params.put("College", spnCollege.getSelectedItem().toString());
		if(spnGrade.getSelectedItemPosition() == 0) params.put("Year","");
		else params.put("Year", spnGrade.getSelectedItem().toString());
		if(spnMajor.getSelectedItemPosition() == 0) params.put("Major","");
		else params.put("Major", spnMajor.getSelectedItem().toString());
		if(spnClass.getSelectedItem().equals("未指定")){
			params.put("Class", "");
		}else{
			params.put("Class", spnClass.getSelectedItem().toString());
		}
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
				dismissProgressDialog();
            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！"+error.toString())  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
				dismissProgressDialog();
                //解析数据
				SeniorScore seniorScore = null;
                try {
                	seniorScore = SeniorScore.fromJson(content);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
	                .setTitle("错误")  
	                .setMessage("服务器返回：" + content)  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //如果为空
    			if(seniorScore == null) {
	            	new AlertDialog.Builder(DormScoreAdvancedActivity.this)    
	                .setTitle("提示")  
	                .setMessage("没有相关信息！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}
                //访问成功
                Intent intent = new Intent();
                intent.putExtra("Week", spnWeek.getSelectedItem().toString());
                intent.putExtra("Year", spnYear.getSelectedItem().toString());
                intent.putExtra("Level", spnLevel.getSelectedItem().toString());
                intent.putExtra("Type", spnType.getSelectedItem().toString());
                intent.putExtra("YouCha", spnScoreLevel.getSelectedItem().toString());
                intent.putExtra("IsOfClasses", spnIsOfClasses.getSelectedItem().toString());
                intent.putExtra("College",spnCollege.getSelectedItem().toString());
                intent.putExtra("Grade", spnGrade.getSelectedItem().toString());
                intent.putExtra("Major", spnMajor.getSelectedItem().toString());   
                intent.putExtra("Jsonscore", seniorScore.toJson());
                intent.setClass(DormScoreAdvancedActivity.this, DormScoreAdvancedInfoActivity.class);
                startActivity(intent);
			}
        	
        });  
    	
    }
    
    
}