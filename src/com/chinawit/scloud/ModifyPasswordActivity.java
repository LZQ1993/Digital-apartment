package com.chinawit.scloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class ModifyPasswordActivity extends NavBarActivity {
    
    private EditText edtPwdold;
    private EditText edtPwdnew;
    private EditText edtPwdcon;
    
    /** 
     * onCreate 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initNavBar("修改密码","<返回",null);
        edtPwdold = (EditText) findViewById(R.id.modify_password_edt_pwdold);
        edtPwdnew = (EditText) findViewById(R.id.modify_password_edt_pwdnew);
        edtPwdcon = (EditText) findViewById(R.id.modify_password_edt_pwdcon);
    }
    
    /**
     * 提交条件
     */
    public void onSubmit(View view) {
        if(edtPwdold.getText().toString().equals("")){
            Toast.makeText(this,"请输入原密码", Toast.LENGTH_SHORT).show();
        }
        else if(edtPwdnew.getText().toString().equals("")) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
        }
        else if(!(edtPwdcon.getText().toString().equals(edtPwdnew.getText().toString()))) {
            Toast.makeText(this, "两次新密码不一样", Toast.LENGTH_SHORT).show();
        }
        else {
            modifyPasswordTask();
        }
    }

    /**
     * 密码修改网络任务
     */
    public void modifyPasswordTask() {
        showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "ModifyPassword.ashx";
        RequestParams params = new RequestParams();
        params.put("UserId", UserInfo.getUser().getId() + "");
        params.put("OldPassword", edtPwdold.getText().toString());
        params.put("NewPassword", edtPwdnew.getText().toString());        
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(ModifyPasswordActivity.this)    
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
                    new AlertDialog.Builder(ModifyPasswordActivity.this)    
                    .setTitle("错误")  
                    .setMessage("服务器通信错误，请与管理员取得联系！")  
                    .setPositiveButton("确定",null)
                    .show();
                }
                //修改成功！
                else if (content.equals("修改成功")) {
                    new AlertDialog.Builder(ModifyPasswordActivity.this)    
                    .setTitle("提示")  
                    .setMessage("密码修改成功，请重新登录用户。")  
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
                    new AlertDialog.Builder(ModifyPasswordActivity.this)    
                    .setTitle("错误")  
                    .setMessage("服务器返回：" + content)  
                    .setPositiveButton("确定",null)
                    .show();                    
                }
            }

        });
    }

}
