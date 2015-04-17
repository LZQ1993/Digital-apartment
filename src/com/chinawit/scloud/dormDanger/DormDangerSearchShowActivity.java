package com.chinawit.scloud.dormDanger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinawit.scloud.MainActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.Danger;
import com.chinawit.scloud.bean.DangerInfo;
import com.chinawit.scloud.dormScore.DormScoreShowActivity;

public class DormDangerSearchShowActivity extends NavBarActivity{
  
	private TextView watchMan;
	private TextView submintTime;
	private TextView state;
	private TextView type;
	private TextView spot;
	private TextView description;
	private TextView proceedMethod;
	private TextView college;
	private TextView proceedTime;
	
	private DangerInfo dangerInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_danger_search_show);
		initNavBar("详细隐患信息", "<返回", "首页");
		initUi();
		initData();
	}

	

	private void initUi() {
		watchMan = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_watchMan);
		submintTime = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_submitTime);
		state = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_state);
		type = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_type);
		spot = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_spot);
		description = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_description);
		proceedMethod = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_proceedMethod);
		proceedTime = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_proceedTime);
		college = (TextView) findViewById(R.id.dorm_danger_seacher_show_tv_college);
		
	}
	private void initData() {
		 //获取信息
       dangerInfo = new DangerInfo();
		try {
			dangerInfo = DangerInfo.fromJson(getIntent().getStringExtra("json"));
			if (dangerInfo == null) { 
				new AlertDialog.Builder(this)    
		        .setTitle("提示")
		        .setMessage("暂时无隐患信息。")  
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
	        .setMessage("数据解析异常")  
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
		
		watchMan.setText(dangerInfo.getWatchMan());
		college.setText(dangerInfo.getCollege());
		submintTime.setText(dangerInfo.getSubmitTime());
		if(dangerInfo.getState().equals("0")){
			state.setText("未处理");
		}else{
			state.setText("已处理");
		}
		type.setText(dangerInfo.getTroubleType());
		spot.setText(dangerInfo.getTroubleSpot());
		description.setText(dangerInfo.getDescription());
		proceedMethod.setText(dangerInfo.getProceedMethod());
		proceedTime.setText(dangerInfo.getProceedTime());
		
	}
	
	  /**
     * 导航右键
     */
	
	
    @Override
    public void onNavBarRightButtonClick(View view) {
    	Intent intent = new Intent();
        intent.setClass(DormDangerSearchShowActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
	
}
