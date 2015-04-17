package com.chinawit.scloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinawit.scloud.R;
import com.chinawit.scloud.app.UserInfo;


public class UserInfoActivity extends NavBarActivity {
    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initNavBar("账户信息", "<返回", null);
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
    	TextView tvUserNum= (TextView) findViewById(R.id.user_info_tv_usernum);
        TextView tvUserName = (TextView) findViewById(R.id.user_info_tv_username);
        TextView tvUserRealname = (TextView) findViewById(R.id.user_info_tv_userrealname);
        TextView tvUsertype = (TextView) findViewById(R.id.user_info_tv_usertype);
        TextView tvCollege = (TextView) findViewById(R.id.user_info_tv_college);
        TextView tvOpenTime = (TextView) findViewById(R.id.user_info_tv_opentime);
        
        tvUserNum.setText("用户编号：" + UserInfo.getUser().getId());
        tvUserName.setText("用户昵称：" + UserInfo.getUser().getUserName());
        tvUserRealname.setText("真实姓名：" + UserInfo.getUser().getRealName());
        tvUsertype.setText("帐号类型：" + UserInfo.getUser().getType());
        tvCollege.setText("帐号所属：" + UserInfo.getUser().getCollege());
        tvOpenTime.setText("开启时间：" + UserInfo.getUser().getBindTime());
    }

    public void modify(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ModifyPasswordActivity.class);
        startActivityForResult(intent, REQUEST_DEFAULT);
    }
    public void edit(View view) {
        Intent intent = new Intent();
        intent.setClass(this, EditActivity.class);
        startActivityForResult(intent, REQUEST_DEFAULT);
    }
    /**
     * activity返回值
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DEFAULT) {
            if (resultCode == RESULT_OK) { //密码修改成功后需要注销
                UserInfo.signOut();
                Intent intent = new Intent();
                intent.putExtra("goto", UserInfoActivity.class.getName());
                intent.setClass(UserInfoActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 注销
     */
    public void logOut(View view) {
        new AlertDialog.Builder(this) 
        .setTitle("确定注销登陆？")
        .setIcon(R.drawable.ic_launcher)
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                UserInfo.signOut();
                Intent intent = new Intent();
                intent.putExtra("goto", UserInfoActivity.class.getName());
                intent.setClass(UserInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(UserInfoActivity.this, "账户已注销", Toast.LENGTH_SHORT).show();
                finish();
            }        
        })
        .setNegativeButton("取消", null)
        .show();
    }

}
