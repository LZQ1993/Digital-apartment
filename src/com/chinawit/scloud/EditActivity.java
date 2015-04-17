package com.chinawit.scloud;

import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.User;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditActivity extends NavBarActivity{
	private EditText userType;
	private EditText account;
	private EditText realName;
	private EditText tel;
	private Spinner college;
	private Spinner major;
	private Spinner year;
	private Spinner classAbb;
	private User user;
	final String[] strsCollege = new String[] {
			"未指定",		
			"电子与信息工程学院",
			"安全科学与工程学院",
			"电气与控制工程学院",
			"工商管理学院",
			"矿业技术学院",
			"营销学院",
			"软件学院",
			"学生处",
			"公安处",
			"校领导"
		};
	final String[] strsType = new String[] {
			"院级学生干部",
			"校级学生干部",
			"班导师",
			"辅导员",
			"院领导",
			"学生处",
			"校职能处",
			"校领导"
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initNavBar("账户编辑", "<返回", null);
		initUI();
	    }

		public void initUI() {
			account = (EditText) findViewById(R.id.edit_account);
			realName = (EditText) findViewById(R.id.edit_realName);
			tel = (EditText) findViewById(R.id.edit_tel);
			userType = (EditText) findViewById(R.id.edit_userType);
			college = (Spinner) findViewById(R.id.edit_college);
			major = (Spinner) findViewById(R.id.edit_major);
			year = (Spinner) findViewById(R.id.edit_year);
			classAbb = (Spinner) findViewById(R.id.edit_classAbb); 	
			
			
			account.setText(UserInfo.getUser().getUserName());
			realName.setText(UserInfo.getUser().getRealName());
			tel.setText(UserInfo.getUser().getTel());
			userType.setText(UserInfo.getUser().getType());
			

			String[] strsMajor = new String[] {"未指定",UserInfo.getUser().getProfessional()};
			String[] strsYear= null;
			Time t = new Time();
			t.setToNow();
			strsYear = new String[6];
			strsYear[0] = "未指定";
			for (int n = 1; n < strsYear.length; n++) {
				strsYear[n] = (t.year - n + 1) + "";
			}
			String[] strsClassAbb = new String[] {"未指定",UserInfo.getUser().getClassAbb()};
			
			ArrayAdapter<String> adpCollege = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsCollege);
			ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsMajor);
			ArrayAdapter<String> adpYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsYear);
			ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strsClassAbb);
			
			adpCollege.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adpYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			college.setAdapter(adpCollege);
			major.setAdapter(adpMajor);
			year.setAdapter(adpYear);
			classAbb.setAdapter(adpClassAbb);
			
			if(!(UserInfo.getUser().getType().equals(strsType[0])||UserInfo.getUser().getType().equals(strsType[1]))){
				for(int i = 0;i<strsCollege.length;i++){
					if(UserInfo.getUser().getCollege().equals(strsCollege[i])){
						college.setSelection(i);	
					   }
					}
				major.setEnabled(false);
				year.setEnabled(false);
				classAbb.setEnabled(false);	
				major.setSelection(0);
				year.setSelection(0);
				classAbb.setSelection(0);
				
			}else{
				for(int i = 0;i<strsCollege.length;i++){
					if(UserInfo.getUser().getCollege().equals(strsCollege[i])){
						college.setSelection(i);	
					   }
					major.setEnabled(true);
					year.setEnabled(true);
					classAbb.setEnabled(true);
					getMajorByCollegeTask();
					year.setSelection(0);
					classAbb.setSelection(1);
					
			    }
				
			
			college.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if(!(UserInfo.getUser().getType().equals(strsType[0])||UserInfo.getUser().getType().equals(strsType[1]))){
						major.setEnabled(false);
						year.setEnabled(false);
						classAbb.setEnabled(false);	
					}else{
						major.setEnabled(true);
						year.setEnabled(true);
						classAbb.setEnabled(true);
						getMajorByCollegeTask();
						year.setSelection(0);
						if(UserInfo.getUser().getCollege().equals(college.getSelectedItem().toString())){
							String[] strsClassAbb = new String[] {"未指定",UserInfo.getUser().getClassAbb()};
							ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
			        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        		classAbb.setAdapter(adpClassAbb);
							classAbb.setSelection(1);	
						}else{
							String[] strsClassAbb = new String[] {"未指定"};
							ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
			        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        		classAbb.setAdapter(adpClassAbb);
							classAbb.setSelection(0);
						}
					}
		   }
					
							
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
			});
			
			major.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			year.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {	
						if(position!=0){
							getClassByConditionTask();		
						}
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
	
		}
	   }
		public void edited(View view){
				if(tel.getText().toString().equals("")){	
					 new AlertDialog.Builder(EditActivity.this)    
		                .setTitle("提示")  
		                .setMessage("您必须输入一个电话号码!")  
		                .setPositiveButton("确定",null)
		                .show();
					 return;
		            }
				if(college.getSelectedItemPosition() == 0){
					new AlertDialog.Builder(EditActivity.this)    
		            .setTitle("提示")  
		            .setMessage("您必须指定一个单位!")  
		            .setPositiveButton("确定", null)
		            .show(); 
					major.setSelection(0);
		    	    return;
				}
				if(UserInfo.getUser().getType().equals(strsType[0])||UserInfo.getUser().getType().equals(strsType[1])){
					if(major.getSelectedItemPosition() == 0){
						new AlertDialog.Builder(EditActivity.this)    
			            .setTitle("提示")  
			            .setMessage("您必须指定一个专业!")  
			            .setPositiveButton("确定", null)
			            .show(); 
						year.setSelection(0);
						return;
					}
					if(year.getSelectedItemPosition() == 0){
						new AlertDialog.Builder(EditActivity.this)    
			            .setTitle("提示")  
			            .setMessage("您必须指定一个年级!")  
			            .setPositiveButton("确定", null)
			            .show(); 
						classAbb.setSelection(0);
						return;
				    }
					if(classAbb.getSelectedItemPosition() == 0){
						new AlertDialog.Builder(EditActivity.this)    
			            .setTitle("提示")  
			            .setMessage("您必须指定一个班级!")  
			            .setPositiveButton("确定", null)
			            .show(); 
						return;
					  }
					}
				userModify();
     }
		/**
		 * 通过学院获取专业
		 */
		public void getMajorByCollegeTask() {	
			//showProgressDialog();
			String url = NetworkInfo.getServiceUrl() + "GetProfessionalList.ashx";
			RequestParams params = new RequestParams();
			params.put("College", college.getSelectedItem().toString());		
			HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable error) {
	                dismissProgressDialog();
	            	new AlertDialog.Builder(EditActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	//清除学院
	            	college.setSelection(0);
				}

				@Override
				public void onSuccess(String content) {
	                //dismissProgressDialog();
	                String[] tempsMajor = null;
	                try {
	                	tempsMajor =JsonUtil.fromJson(content, String[].class);
	                } catch (Exception e) {
		            	new AlertDialog.Builder(EditActivity.this)    
		                .setTitle("错误")  
		                .setMessage("数据解析异常")  
		                .setPositiveButton("确定", null)
		                .show();
		            	//清除学院
		            	college.setSelection(0);
	                	return;
	                }
	    			//访问成功
	                
	                String[] strsMajor = new String[tempsMajor.length + 1];
	                strsMajor[0] = "未指定";
	                for (int n=0; n < tempsMajor.length; n++) {
	                	strsMajor[n+1] = tempsMajor[n];
	                }
	        		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, strsMajor);
	        		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		major.setAdapter(adpMajor);
	        		for(int i = 0;i<strsMajor.length;i++){
						if(UserInfo.getUser().getProfessional().equals(strsMajor[i])){
							major.setSelection(i);	
						   }
						}
	        		
				}

			});
		}
		/**
		 * 通过条件获取班级
		 */
		public void getClassByConditionTask() {		
			//showProgressDialog();
			if(college.getSelectedItemPosition() == 0){
				new AlertDialog.Builder(EditActivity.this)    
	            .setTitle("提示")  
	            .setMessage("您必须指定一个单位!")  
	            .setPositiveButton("确定", null)
	            .show(); 
				major.setSelection(0);
	    	    return;
			}else if(major.getSelectedItemPosition() == 0){
				new AlertDialog.Builder(EditActivity.this)    
	            .setTitle("提示")  
	            .setMessage("您必须指定一个专业!")  
	            .setPositiveButton("确定", null)
	            .show(); 
				year.setSelection(0);
			}else if(year.getSelectedItemPosition() == 0){
				new AlertDialog.Builder(EditActivity.this)    
	            .setTitle("提示")  
	            .setMessage("您必须指定一个年级!")  
	            .setPositiveButton("确定", null)
	            .show(); 
				classAbb.setSelection(0);
			}else{
			String url = NetworkInfo.getServiceUrl() + "GetClassList.ashx";
			RequestParams params = new RequestParams();
			params.put("department", college.getSelectedItem().toString());
			params.put("Professional", major.getSelectedItem().toString());
			params.put("AcademicYear", year.getSelectedItem().toString());
			HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable error) {
	              //  dismissProgressDialog();
	            	new AlertDialog.Builder(EditActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！"+error.toString())  
	                .setPositiveButton("确定",null)
	                .show();
	        		//清除班级
	            	classAbb.setSelection(0);
	            	return;
				}

				@Override
				public void onSuccess(String content) {
	                dismissProgressDialog();
	                String[] tempsClassAbb = null;
	                try {
	                	tempsClassAbb = JsonUtil.fromJson(content, String[].class);
	                } catch (Exception e) {
		            	new AlertDialog.Builder(EditActivity.this)    
		                .setTitle("错误")  
		                .setMessage("服务器返回：" + content)  
		                .setPositiveButton("确定", null)
		                .show();
		        		//清除班级
		            	classAbb.setSelection(0);
	                	return;
	                }
	    			//访问成功
	                String[] strsClassAbb = new String[tempsClassAbb.length + 1];
	                strsClassAbb[0] = "未指定";
	                for (int n=0; n < tempsClassAbb.length; n++) {
	                	strsClassAbb[n+1] = tempsClassAbb[n];
	                }
	        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(EditActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
	        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        		classAbb.setAdapter(adpClassAbb);
	        		for(int i = 0;i<strsClassAbb.length;i++){
						if(UserInfo.getUser().getClassAbb().equals(strsClassAbb[i])){
							classAbb.setSelection(i);	
						   }
						}
				}
			});
		   }
		}

		private void userModify() {
			user = new User();
			user.setType(userType.getText().toString());
			user.setUserName(account.getText().toString());
			user.setRealName(realName.getText().toString());
			user.setCollege(college.getSelectedItem().toString());
			user.setTel(tel.getText().toString());
			if(!(UserInfo.getUser().getType().equals(strsType[0])||UserInfo.getUser().getType().equals(strsType[1]))){
				user.setProfessional("无");
				user.setClassAbb("无");
			}else{
				user.setProfessional(major.getSelectedItem().toString());
				user.setClassAbb(classAbb.getSelectedItem().toString());
			}
			showProgressDialog();
	        String url = NetworkInfo.getServiceUrl() + "UserModify.ashx";
	        RequestParams params = new RequestParams();
	        params.put("json", user.toJson());
	        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

	            @Override
	            public void onFailure(Throwable error) {
	                dismissProgressDialog();
	                new AlertDialog.Builder(EditActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！")  
	                .setPositiveButton("确定",null)
	                .show();
	            }

	            @Override
	            public void onSuccess(String content) {
	                dismissProgressDialog();
	                //网络访问错误
	                if (content == null || content.equals("") || content.equals("null")) {
	                    new AlertDialog.Builder(EditActivity.this)    
	                    .setTitle("错误")  
	                    .setMessage("服务器通信错误，请与管理员取得联系！")  
	                    .setPositiveButton("确定",null)
	                    .show();
	                    return;
	                }
	                //修改成功！
	                else if (content.equals("1")) {
	                    new AlertDialog.Builder(EditActivity.this)    
	                    .setTitle("提示")  
	                    .setMessage("修改成功，请重新登录用户。")  
	                    .setPositiveButton("确定",new OnClickListener() {
	                        @Override
	                        public void onClick(DialogInterface dialog, int which) {
	                            setResult(RESULT_OK);
	                            finish();
	                        }
	                    })
	                    .setCancelable(false)//不允许退出
	                    .show();
	                } else {
	                    //修改失败
	                    new AlertDialog.Builder(EditActivity.this)    
	                    .setTitle("错误")  
	                    .setMessage("修改失败!" )  
	                    .setPositiveButton("确定",null)
	                    .show();  
	                    return;
	                }
	            }

	        });
	    }

}