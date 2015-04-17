package com.chinawit.scloud.dormSearch;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.chinawit.scloud.bean.DormList;
import com.chinawit.scloud.bean.Dorm_lst;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormNameActivity extends NavBarActivity  {

	/** 
	 * onCreate 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_seacher_name);
		initNavBar("寝室列表", "<返回", null);
		initWidget();
	}

	/**
	 * 初始化控件
	 */
	private void initWidget() {

		//获取信息
		DormList Dormlist = null;
			try {
				Dormlist = DormList.fromJson(getIntent().getStringExtra("JsonDormlist"));
				if (Dormlist.getDormlistCount() == 0) { //无人
					new AlertDialog.Builder(this)    
				       .setTitle("提示")
				       .setMessage("该寝室暂时无人居住。")  
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
				else{
					showOneRoom(Dormlist);
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
		}
	 /**
     * 显示结果
     */
    private void showOneRoom(final DormList room) {
    	LinearLayout ll_result = (LinearLayout) findViewById(R.id.dorm_info_lll);
    	//清理所有子项
		ll_result.removeAllViews();
		//ll_result.setBackgroundColor(0xFF999999); //灰白
		//ll_result.setBackgroundColor(0xFFEEEEEE);   //浅蓝
				
	
		//mate_属性
		LayoutParams lp_mate = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp_mate.setMargins(20,10,20,10);;
	
		//添加成员
		for(int n=0; n<room.getDormlistCount(); n++) {
			final Dorm_lst mate = room.getDorm_lst().get(n);
			String str_mate = ""+
					"\n"+mate.getDormNum()+"		"+mate.getCollege()+"\n"+
					"";
			//mate信息
			Button btnMate = new Button(this);
			// TODO
			btnMate.setId(n + 1); // 不安全写法
			// TODO 
			btnMate.setText(str_mate);
			btnMate.setTextColor(0xFF000000);
			btnMate.setTextSize(20);
			btnMate.setBackgroundColor(0xFFEEEEEE);
			
			btnMate.setGravity(Gravity.CENTER);
			//监听器
			btnMate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//权限充足
					// TODO id值可能不安全
					getDormPlateByDormNumTask(mate.getDormNum());
					// TODO	
				}
			});
			//添加到框架中
			ll_result.addView(btnMate,lp_mate);
		}
    }
	
	/**
	 * 通过寝室号获取门牌
	 */
	public void getDormPlateByDormNumTask(String dormNum) {
		showProgressDialog();		
		String url = NetworkInfo.getServiceUrl() + "GetDormPlateByDormNum.ashx";
		RequestParams params = new RequestParams();
		params.put("DormNum",dormNum);
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
                dismissProgressDialog();
            	new AlertDialog.Builder(DormNameActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
			}

			@Override
			public void onSuccess(String content) {
                dismissProgressDialog();
    			//解析数据
    			DormInfo dormInfo = null;
                try {
                	dormInfo = DormInfo.fromJson(content);
                } catch (Exception e) {
	            	new AlertDialog.Builder(DormNameActivity.this)    
	                .setTitle("错误")  
	                .setMessage("服务器返回：" + content)  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //数据为空
    			if (dormInfo == null ||dormInfo.getDormNum() == null || dormInfo.getDormNum().equals("")) {
	            	new AlertDialog.Builder(DormNameActivity.this)    
	                .setTitle("提示")  
	                .setMessage("未获取到寝室信息\n或该寝室不存在！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}
    			
    			//权限充足
                Intent intent = new Intent();
                intent.putExtra("jsonDormPlate",dormInfo.toJson());
                intent.setClass(DormNameActivity.this, DormInfoActivity.class);
                startActivity(intent);
			}
		});

	}
}

