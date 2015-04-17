package com.chinawit.scloud;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.PhoneInfo;
import com.chinawit.scloud.app.SettingInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.User;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends NavBarActivity{
	 private EditText edt_username;
	 private EditText edt_password;
	 private CheckBox cb_savePwd;
	   /** 
     * onCreate 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initNavBar("账户登录", "<返回",null);
        initWidget();       
	}
	/**
     * 初始化控件
     */
    private void initWidget() {
        edt_username = (EditText) findViewById(R.id.login_edt_username);
        edt_password = (EditText) findViewById(R.id.login_edt_password);
        cb_savePwd = (CheckBox) findViewById(R.id.login_cb_savePwd);
        cb_savePwd.setChecked(SettingInfo.isSavePassword());
        if (cb_savePwd.isChecked()) {
            edt_username.setText(SettingInfo.getSavedUsername());
            edt_password.setText(SettingInfo.getSavedPassword());
        }
    }
    
    
    /**
     * 注册
     */
    public void register(View view) {
       Intent intent = new Intent();
       intent.setClass(this, RegisterActivity.class);
       startActivity(intent);
       
        
    }

    /**
     * 执行登录
     */
    public void onLogin(View view) {
       if (edt_username.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show();
        }
        else if(edt_password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
        } else {
            loginTask();
        }
        
    }
    /**
     * 登录网络任务
     */
    private void loginTask() {  
    	
    	//新加设备码判断
    	//设备码不为空-获取本地设备码
        String IMEI = PhoneInfo.getDeviceId();
        //未获得设备码
        if (IMEI == null || IMEI.equals("")) {
            new AlertDialog.Builder(LoginActivity.this)    
            .setTitle("提示")  
            .setMessage("未获取到设备标识！")  
            .setPositiveButton("确定",null)
            .show();
            return;
        }else{
    	 showProgressDialog();
         String url = NetworkInfo.getServiceUrl() + "Logon.ashx";        
         RequestParams params = new RequestParams();
         params.put("UserName", edt_username.getText().toString());
         params.put("Password", edt_password.getText().toString());
         params.put("EquipmentNum",IMEI);
         HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

             @Override
             public void onFailure(Throwable error) {
                 dismissProgressDialog();
                 new AlertDialog.Builder(LoginActivity.this)
                 .setTitle("错误")
                 .setMessage("网络访问失败，请检查网络！"+error.toString())
                 .setPositiveButton("确定",null)
                 .show(); 
             }

             @Override
             public void onSuccess(String content) {
                 dismissProgressDialog();
                 User user = null;
                 try {
                   if(content.equals("登录失败")){
                	   new AlertDialog.Builder(LoginActivity.this)    
                       .setTitle("提示")  
                       .setMessage("设备码错误或用户不存在")  
                       .setPositiveButton("确定",null)
                       .show();
                       return;
                   }
                   if(content.equals("密码错误")){
                	   new AlertDialog.Builder(LoginActivity.this)    
                       .setTitle("提示")  
                       .setMessage("密码错误")  
                       .setPositiveButton("确定",null)
                       .show();
                       return;
                   }if(content.equals("验证未通过")){
                	   new AlertDialog.Builder(LoginActivity.this)    
                       .setTitle("提示")  
                       .setMessage("验证未通过")  
                       .setPositiveButton("确定",null)
                       .show();
                       return;
                   }
                	   user = User.fromJson(content);
                   
                 } catch(Exception e) {
                     new AlertDialog.Builder(LoginActivity.this)    
                     .setTitle("错误")  
                     .setMessage("数据解析异常")  
                     .setPositiveButton("确定",null)
                     .show();
                     return;
                 }
                 
                 //用户不存在
                 if (user == null || user.getId() == 0) {
                     new AlertDialog.Builder(LoginActivity.this)    
                     .setTitle("提示")  
                     .setMessage("密码错误或者用户不存在！")  
                     .setPositiveButton("确定",null)
                     .show();
                     return;
                 }
                 //账户停用
                 if(user.getIsStop().equals("1")){
                     new AlertDialog.Builder(LoginActivity.this)    
                     .setTitle("提示")  
                     .setMessage("当前账户已停用，请您联系管理员进行激活")  
                     .setPositiveButton("确定",null)
                     .show(); 
                     return;
                 }
                 //登录成功
                 UserInfo.signIn(user);
                 Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                 //记住密码
                 if(cb_savePwd.isChecked()) {
                     SettingInfo.setSavedUsername(edt_username.getText().toString());
                     SettingInfo.setSavedPassword(edt_password.getText().toString());
                 } else {
                     SettingInfo.clearSavedInfo();
                 }
                 SettingInfo.setSavePassword(cb_savePwd.isChecked());
                 //用户信息页面跳转
                 Class<?> gotoClz = null;
                 try {
                     gotoClz = Class.forName(getIntent().getStringExtra("goto"));
                 } catch (Exception e) {
                 }
                 if (gotoClz != null) {
                     Intent intent = new Intent(LoginActivity.this, gotoClz);
                     startActivity(intent);
                 }
                 finish();
             }

         });
        }
     }
    
/*
    *//**
     * 设备码绑定网络任务 
     *//*
    private void bindIMEITask(int id) {
        showProgressDialog();
        //获取本地设备码
        String IMEI = PhoneInfo.getDeviceId();
        if (IMEI == null || IMEI.equals("")) {
            new AlertDialog.Builder(LoginActivity.this)    
            .setTitle("提示")  
            .setMessage("未获取到设备标识，绑定失败！")  
            .setPositiveButton("确定",null)
            .show();
            dismissProgressDialog();
            return;
        }
        //网络通信
        String url = NetworkInfo.getServiceUrl() + "SetEquipmentNum.ashx";
        RequestParams params = new RequestParams();
        params.put("Id", String.valueOf(id));
        params.put("EquipmentNum",IMEI);
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(LoginActivity.this)
                .setTitle("错误")
                .setMessage("网络访问失败，请检查网络！"+error.toString())
                .setPositiveButton("确定",null)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                if (content.equals("绑定成功")) { //设备绑定成功
                    new AlertDialog.Builder(LoginActivity.this)    
                    .setTitle("提示")  
                    .setMessage("设备绑定成功！")  
                    .setPositiveButton("确定",new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            loginTask(); //重新调用登录任务
                        }
                    })
                    .setCancelable(false)
                    .show(); 
                } else {
                    new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("提示")
                    .setMessage("设备绑定失败，可能的原因为：\n" + content)
                    .setPositiveButton("确定",null)
                    .show();
                }
            }

        });
    }*/

}
