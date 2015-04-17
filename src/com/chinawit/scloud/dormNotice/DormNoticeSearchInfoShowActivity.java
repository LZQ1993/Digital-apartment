package com.chinawit.scloud.dormNotice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.NoticeInfo;

public class DormNoticeSearchInfoShowActivity extends NavBarActivity{
	 private TextView  tvInfo;
	 private Switch SthNotice;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_notice_search_info_show);
		initNavBar("详细通知","<返回", null);
		tvInfo = (TextView) findViewById(R.id.dorm_notice_search_info_tv_info);
		SthNotice = (Switch) findViewById(R.id.dorm_notice_search_info_show_sth);
 		NoticeResultShow();
 	/*	SthNotice.setOnCheckedChangeListener(new OnCheckedChangeListener() {  		
 		@Override  
 		public void onCheckedChanged(CompoundButton buttonView,  
 			  boolean isChecked) {  
 			 // TODO Auto-generated method stub  
 			 if (isChecked) {  
 			    
 			 } else {  
 			        
 			   }  
 			 }  
 		}); */ 

		}

		public void NoticeResultShow() {
			NoticeInfo noticeInfo  = null;
			try {
				noticeInfo = NoticeInfo.fromJson(getIntent().getStringExtra("noticeResult"));
				if (noticeInfo==null) { //无人
					new AlertDialog.Builder(this)    
			        .setTitle("提示")
			        .setMessage("暂时无通知信息。")  
			        .setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					})
			        .setCancelable(false)
			        .show();
					return; //跳出
				}
			} catch(Exception e) {
				new AlertDialog.Builder(this)    
		        .setTitle("数据错误")
		        .setMessage("数据解析失败，请重试。")  
		        .setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
		        .setCancelable(false)
		        .show();
				return; //跳出
			}
	        //填充
	        StringBuffer sb = new StringBuffer();
	        sb.append("所属学院：" + noticeInfo.getCollege()+ "\n");
	        sb.append("通知类型：" + noticeInfo.getNoticeType()+ "\n");
	        sb.append("通知内容：" + noticeInfo.getNoticeContent() + "\n");
	        if(noticeInfo.getState().equals("1")){
	        	 sb.append("状态：" + "有效通知" + "\n");
            }else{
            	 sb.append("状态：" + "无效通知"+ "\n");
            }
	       
	        sb.append("截至时间：" + noticeInfo.getRemindTime() + "\n");
	        //填充
	        tvInfo.setText(sb.toString());
		}
		
		
}
		