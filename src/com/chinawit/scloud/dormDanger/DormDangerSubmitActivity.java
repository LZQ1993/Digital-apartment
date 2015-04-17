package com.chinawit.scloud.dormDanger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.chinawit.scloud.LoginActivity;
import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.DangerInfo;
import com.chinawit.scloud.bean.DangerSResultInfo;
import com.chinawit.scloud.bean.DangerSubmitResult;
import com.chinawit.scloud.bean.SubmitDanger;
import com.chinawit.scloud.dormSearch.DormSearchMainActivity;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormDangerSubmitActivity extends NavBarActivity{
   private Spinner spnType;
   private Spinner spnPlace;
   private EditText edtDanger;
   
   private SubmitDanger danger;
   private DangerSubmitResult dangerSubmitResult;
   private DangerSResultInfo dangerSResultInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_danger_submit);
		initNavBar("隐患报送", "<返回", null);
		spnType = (Spinner) findViewById(R.id.dorm_danger_submit_type_spn);
		spnPlace = (Spinner) findViewById(R.id.dorm_danger_submit_place_spn);
		edtDanger = (EditText) findViewById(R.id.dorm_danger_submit_edt);	
	}
	
	 public void dangerSubmit(View view) {
		    /*//用户未登录
			if (UserInfo.getUser() == null) {
				new AlertDialog.Builder(DormDangerSubmitActivity.this)    
	             .setTitle("提示")  
	             .setMessage("该功能模块，仅特定权限账户才能使用。您未登录账户。\n您确定要登录账户？")  
	             .setPositiveButton("确定",new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent();
							intent.setClass(DormDangerSubmitActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					})
	             .setNegativeButton("取消",null)
	             .show();
					return;
				}
			//权限不足
			if (UserInfo.getUser().getPower()<1) {
					new AlertDialog.Builder(DormDangerSubmitActivity.this)    
	                .setTitle("提示")  
	                .setMessage("权限不足！\n您可以尝试与管理员联系，以便获得更多的授权。")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
			}*/
			if(spnType.getSelectedItemPosition()==0||spnPlace.getSelectedItemPosition()==0||edtDanger.getText().toString().equals("")){
						 new AlertDialog.Builder(DormDangerSubmitActivity.this)    
				            .setTitle("提示")  
				            .setMessage("您必须填写相应的隐患内容")  
				            .setPositiveButton("确定", null)
				            .show(); 
						    return;
					 }
			danger = new SubmitDanger();
			danger.setDescription(edtDanger.getText().toString());
			danger.setTroubleSpot(spnPlace.getSelectedItem().toString());
			danger.setSubmitPeopleID(UserInfo.getUser().getId()+"");
			danger.setTroubleType(spnType.getSelectedItem().toString());
			Submit();
	 }

	private void Submit() {
		  showProgressDialog(); 
		  String url = NetworkInfo.getServiceUrl() + "uploadTrouble.ashx";
		  RequestParams params = new RequestParams();
		  params.put("json", danger.toJson());
		  HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
	            @Override
	            public void onFailure(Throwable error) {
	                dismissProgressDialog();
	                new AlertDialog.Builder(DormDangerSubmitActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败！\n请您在检查网络情况后重试。")  
	                .setPositiveButton("确定",null)
	                .show();
	            }

	            @Override
	            public void onSuccess(String content) {
	                dismissProgressDialog();
	                try {
	                	dangerSubmitResult = new DangerSubmitResult();
	 	                dangerSResultInfo = new DangerSResultInfo();
	                	dangerSubmitResult =DangerSubmitResult.fromJson(content);
	                	if(dangerSubmitResult.getResultCode().equals("0")){
	                		new AlertDialog.Builder(DormDangerSubmitActivity.this)    
			                .setTitle("错误")  
			                .setMessage("无数据!")  
			                .setPositiveButton("确定", null)
			                .show();
		                	return;
	                	}
	                	dangerSResultInfo = dangerSubmitResult.getResult().get(0);
	                	if(dangerSubmitResult.getResultCode().equals("-1")){		                	
		                		if(dangerSResultInfo.getResult().equals("0")){
		                			if(dangerSResultInfo.getTip().equals("添加失败")){
		                				new AlertDialog.Builder(DormDangerSubmitActivity.this)    
		    			                .setTitle("错误")  
		    			                .setMessage("添加失败!")  
		    			                .setPositiveButton("确定", null)
		    			                .show();
		    		                	return;
		                			}
		                			if(dangerSResultInfo.getTip().equals("用户不存在")){
		                				new AlertDialog.Builder(DormDangerSubmitActivity.this)    
		    			                .setTitle("错误")  
		    			                .setMessage("用户不存在!")  
		    			                .setPositiveButton("确定", null)
		    			                .show();
		    		                	return;
		                			}	
		                		}
		                   }
	                if(dangerSubmitResult.getResultCode().equals("1")){
	                	if(dangerSResultInfo.getResult().equals("1")){
			                if(dangerSResultInfo.getTip().equals("添加成功")){
				                	new AlertDialog.Builder(DormDangerSubmitActivity.this)    
				    			    .setTitle("提示")  
				    			    .setMessage("添加成功!")  
				    			    .setPositiveButton("确定", new OnClickListener() {
							        @Override
							        public void onClick(DialogInterface arg0, int arg1) {
							       finish();
							          }
				    			    })
				    			    .show();
				    		        return;
			                	}
		                }
		                }
	                } catch (Exception e) {
		            	new AlertDialog.Builder(DormDangerSubmitActivity.this)    
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
