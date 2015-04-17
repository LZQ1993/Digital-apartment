package com.chinawit.scloud.dormNotice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;

public class DormNoticeMainActivity extends NavBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_notice_main);
		initNavBar("通知管理", "<返回", null);
	}
	
	/*
	 * 通知发布
	 */
	 public void noticeSubmit(View view) {
			 Intent intent = new Intent();
			 intent.setClass(DormNoticeMainActivity.this,DormNoticeSubmitActivity.class);
             startActivity(intent);
		
	 }
	 
	 /*
	  * 通知查询
	  */
	 public void noticeSearch(View view) {
			 Intent intent = new Intent();
			 intent.setClass(DormNoticeMainActivity.this,DormNoticeSearchActivity.class);
             startActivity(intent);
	 }
		
}
