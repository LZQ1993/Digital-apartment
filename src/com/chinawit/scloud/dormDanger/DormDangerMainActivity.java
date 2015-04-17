package com.chinawit.scloud.dormDanger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;

import com.chinawit.scloud.LoginActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.dormScore.DormScoreAdvancedActivity;
import com.chinawit.scloud.dormScore.DormScoreMainActivity;

public class DormDangerMainActivity extends NavBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_danger_main);
		initNavBar("隐患管理", "<返回", null);
	}

	/*
	 * 隐患报送
	 */
	 public void dangerSubmit(View view) {		 
             if (UserInfo.getUser() == null) { //需要登录
 	            new AlertDialog.Builder(DormDangerMainActivity.this)    
 	            .setTitle("提示")  
 	            .setMessage("该功能仅登录用户才能使用，您未登录。\n您确定要登录账户？")  
 	            .setPositiveButton("确定", new OnClickListener() {
 	                @Override
 	                public void onClick(DialogInterface dialog, int which) {
 	                    Intent intent = new Intent();
 	                    intent.setClass(DormDangerMainActivity.this, LoginActivity.class);
 	                    startActivity(intent);
 	                }
 	            })
 	            .setNegativeButton("取消",null)
 	            .show();            
 	        }
 	        else if (Integer.valueOf(UserInfo.getUser().getPower())>0) {
 	        	Intent intent = new Intent();
 				 intent.setClass(DormDangerMainActivity.this,DormDangerSubmitActivity.class);
 	             startActivity(intent);
 	        } else { //权限不足
 	            new AlertDialog.Builder(this)    
 	            .setTitle("提示")  
 	            .setMessage("您的权限不足，无法使用这个功能。\n请您与管理员取得联系以便获取更多的权限。")  
 	            .setPositiveButton("确定", null)
 	            .show();
 	        }
	 }
	 
	 /*
	  * 隐患查询
	  */
	 public void dangerSearch(View view) {
		 Intent intent = new Intent();
		 intent.setClass(DormDangerMainActivity.this,DormDangerSearchActivity.class);
          startActivity(intent);
	 }
	 
	
}
