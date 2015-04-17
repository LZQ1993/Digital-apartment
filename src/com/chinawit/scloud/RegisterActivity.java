package com.chinawit.scloud;

import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.DormList;
import com.chinawit.scloud.bean.User;
import com.chinawit.scloud.bean.UserNameIsValid;
import com.chinawit.scloud.dormScore.DormScoreAdvancedActivity;
import com.chinawit.scloud.dormScore.DormScoreShowActivity;
import com.chinawit.scloud.dormSearch.DormSearchMainActivity;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author l
 *
 */
public class RegisterActivity extends NavBarActivity {

	private EditText account;
	private EditText password1;
	private EditText password2;
	private EditText realName;
	private EditText tel;
	private Spinner userType;
	private Spinner college;
	private Spinner major;
	private Spinner year;
	private Spinner classAbb;
	private User user;
	private UserNameIsValid userNameIsValid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initNavBar("账户注册", "<返回", null);
		initUI();
	}

	public void initUI() {
		account = (EditText) findViewById(R.id.register_edt_account);
		password1 = (EditText) findViewById(R.id.register_edt_password1);
		password2 = (EditText) findViewById(R.id.register_edt_password2);
		realName = (EditText) findViewById(R.id.register_edt_realName);
		tel = (EditText) findViewById(R.id.register_edt_tel);
		userType = (Spinner) findViewById(R.id.register_edt_userType);
		college = (Spinner) findViewById(R.id.register_edt_college);
		major = (Spinner) findViewById(R.id.register_edt_major);
		year = (Spinner) findViewById(R.id.register_edt_year);
		classAbb = (Spinner) findViewById(R.id.register_edt_classAbb);
		
		String[] strsCollege = new String[] {
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
		String[] strsMajor = new String[] {"未指定"};
		String[] strsYear= null;
		Time t = new Time();
		t.setToNow();
		strsYear = new String[6];
		strsYear[0] = "未指定";
		for (int n = 1; n < strsYear.length; n++) {
			strsYear[n] = (t.year - n + 1) + "";
		}
		String[] strsClassAbb = new String[] {"未指定"};
		
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
		
		
		userType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if(userType.getSelectedItemPosition()==0||userType.getSelectedItemPosition()==1||userType.getSelectedItemPosition()==2){
					college.setSelection(0);
					major.setEnabled(true);
					major.setSelection(0);
					year.setEnabled(true);
					year.setSelection(0);
					classAbb.setEnabled(true);
					classAbb.setSelection(0);
				}else{
					college.setSelection(0);
					major.setEnabled(false);
					major.setSelection(0);
					year.setEnabled(false);
					year.setSelection(0);
					classAbb.setEnabled(false);
					classAbb.setSelection(0);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		college.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if ((position>0&&position<8)&&(userType.getSelectedItemPosition()==0||userType.getSelectedItemPosition()==1||userType.getSelectedItemPosition()==2)) {
					getMajorByCollegeTask();
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
            	new AlertDialog.Builder(RegisterActivity.this)    
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
	            	new AlertDialog.Builder(RegisterActivity.this)    
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
        		ArrayAdapter<String> adpMajor = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, strsMajor);
        		adpMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		major.setAdapter(adpMajor);
        		
			}

		});
	}
	/**
	 * 通过条件获取班级
	 */
	public void getClassByConditionTask() {		
		//showProgressDialog();
		if(college.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个单位!")  
            .setPositiveButton("确定", null)
            .show(); 
			major.setSelection(0);
    	    return;
		}else if(major.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个专业!")  
            .setPositiveButton("确定", null)
            .show(); 
			year.setSelection(0);
		}else if(year.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
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
            	new AlertDialog.Builder(RegisterActivity.this)    
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
	            	new AlertDialog.Builder(RegisterActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
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
        		ArrayAdapter<String> adpClassAbb = new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_item, strsClassAbb);  
        		adpClassAbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		classAbb.setAdapter(adpClassAbb);
			}
		});
	   }
	}
	
	public void submit(View view){
		if(userType.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个账户类型!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(account.getText().toString().equals("")){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须输入用户名!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(password1.getText().toString().equals("")){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须输入密码!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(password2.getText().toString().equals("")){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须输入确认密码!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(!(password2.getText().toString().equals(password1.getText().toString()))){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您输入两次密码不一致!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(realName.getText().toString().equals("")){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须输入真实姓名!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(tel.getText().toString().equals("")){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须输入电话号码!")  
            .setPositiveButton("确定", null)
            .show(); 			
    	    return;
		}
		if(college.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个单位!")  
            .setPositiveButton("确定", null)
            .show(); 
			major.setSelection(0);
    	    return;
		}
		if(userType.getSelectedItemPosition()<3)
		{
		if(major.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个专业!")  
            .setPositiveButton("确定", null)
            .show(); 
			year.setSelection(0);
			return;
		}
		if(year.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个年级!")  
            .setPositiveButton("确定", null)
            .show(); 
			classAbb.setSelection(0);
			return;
	    }
		if(classAbb.getSelectedItemPosition() == 0){
			new AlertDialog.Builder(RegisterActivity.this)    
            .setTitle("提示")  
            .setMessage("您必须指定一个班级!")  
            .setPositiveButton("确定", null)
            .show(); 
			return;
		  }
		}
		userNameIsValid();
	
   }

	private void userNameIsValid() {	
		String url = NetworkInfo.getServiceUrl() + "UserNameIsValid.ashx";
		RequestParams params = new RequestParams();
		userNameIsValid = new UserNameIsValid();
		userNameIsValid.setUserName(account.getText().toString());
		params.put("json", userNameIsValid.toJson());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(RegisterActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
            	return;
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
               
                try { 
                    if(content.equals("0")){
                    	new AlertDialog.Builder(RegisterActivity.this)    
    	                .setTitle("错误")  
    	                .setMessage("用户名已存在!")  
    	                .setPositiveButton("确定", null)
    	                .show();
                    	return;
                    }
                    if(content.equals("-1")){
                    	new AlertDialog.Builder(RegisterActivity.this)    
    	                .setTitle("错误")  
    	                .setMessage("用户已经注册、但未验证通过!")  
    	                .setPositiveButton("确定", null)
    	                .show();
                    	return;
                    }
                    if(content.equals("1")){
                    	regsiterTask();
                    }
                } catch (Exception e) {
	            	new AlertDialog.Builder(RegisterActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }  
			}
		});
	}
	
	private void regsiterTask(){
		user = new User();
		user.setType(userType.getSelectedItem().toString());
		user.setUserName(account.getText().toString());
		user.setPassword(password1.getText().toString());
		user.setRealName(realName.getText().toString());
		user.setCollege(college.getSelectedItem().toString());
		user.setTel(tel.getText().toString());
		if(userType.getSelectedItemPosition()>2){
			user.setProfessional("无");
			user.setClassAbb("无");
		}else{
			user.setProfessional(major.getSelectedItem().toString());
			user.setClassAbb(classAbb.getSelectedItem().toString());
		}
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "UserRegiste.ashx";
		RequestParams params = new RequestParams();
		params.put("json", user.toJson());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(RegisterActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！"+error.toString())  
                .setPositiveButton("确定",null)
                .show();
            	return;
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
               
                try { 
                    if(content.equals("0")){
                    	new AlertDialog.Builder(RegisterActivity.this)    
    	                .setTitle("错误")  
    	                .setMessage("注册失败！")  
    	                .setPositiveButton("确定", null)
    	                .show();
                    	return;
                    }
                    if(content.equals("-1")){
                    	new AlertDialog.Builder(RegisterActivity.this)    
    	                .setTitle("提示")  
    	                .setMessage("用户名验证未通过")  
    	                .setPositiveButton("确定", null)
    	                .show();
                    	return;
                    }
                    if(content.equals("1")){
                    	new AlertDialog.Builder(RegisterActivity.this)    
    	                .setTitle("提示")  
    	                .setMessage("注册成功"+ "\n" +"请于管理员联系激活")  
    	                .setPositiveButton("确定",new OnClickListener() {
    	                    @Override
    	                    public void onClick(DialogInterface dialog, int which) {
    	                    	Intent intent = new Intent();
    	                        intent.setClass(RegisterActivity.this,LoginActivity.class);
    	                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	                        startActivity(intent);
    	                        finish();
    	                    }
    	                })
    	                .show();
                    	return;
                    }
                } catch (Exception e) {
	            	new AlertDialog.Builder(RegisterActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }  
			}
		});
	}
	
}