package com.chinawit.scloud.dormDanger;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.Danger;
import com.chinawit.scloud.bean.DangerSearch;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormDangerSearchActivity extends NavBarActivity{
	   private Spinner spnType;
	   private Spinner spnPlace;
	   private Spinner spnState;
	   private EditText edtKeyWords;
	   private DangerSearch dangerSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_danger_search);
		initNavBar("隐患查询", "<返回", null);
		spnType = (Spinner) findViewById(R.id.dorm_danger_search_type_spn);
		spnPlace = (Spinner) findViewById(R.id.dorm_danger_search_place_spn);
		spnState = (Spinner) findViewById(R.id.dorm_danger_search_state_spn);
		edtKeyWords = (EditText) findViewById(R.id.dorm_danger_search_keyWords_edt);
	}
	
	/*
	 * 隐患search
	 */
	 public void dangerSearch(View view) {	 
		 if(spnPlace.getSelectedItemPosition()==0){
			 new AlertDialog.Builder(DormDangerSearchActivity.this)    
	            .setTitle("提示")  
	            .setMessage("您必须选择一个地点!")  
	            .setPositiveButton("确定", null)
	            .show(); 
			    return;
		 }else{
			 getDangerInfo(); 
 
		 }	  
	 }

	private void getDangerInfo() {
		dangerSearch = new DangerSearch();
		if(spnType.getSelectedItem().toString().equals("全部")){
			dangerSearch.setTroubleType("");
		}else{
			dangerSearch.setTroubleType(spnType.getSelectedItem().toString());
		}
		
		dangerSearch.setTroubleSpot(spnPlace.getSelectedItem().toString());
		if(spnState.getSelectedItem().toString().equals("已处理")){
			dangerSearch.setState("1");
		}
		if(spnState.getSelectedItem().toString().equals("未处理")){
			dangerSearch.setState("0");	
		}
		if(spnState.getSelectedItem().toString().equals("全部")){
			dangerSearch.setState("404");
			}
		dangerSearch.setKeyword(edtKeyWords.getText().toString());
		
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "getTroubleList.ashx";
		RequestParams params = new RequestParams();
		params.put("json", dangerSearch.toJson());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
				dismissProgressDialog();
	            new AlertDialog.Builder(DormDangerSearchActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！")  
	                .setPositiveButton("确定",null)
	                .show();
			}

			@Override
			public void onSuccess(String content) {
				  dismissProgressDialog();
				  Danger danger = null;
				  try { 
					  danger = Danger.fromJson(content);
	                } catch (Exception e) {
		            	new AlertDialog.Builder(DormDangerSearchActivity.this)    
		                .setTitle("错误")  
		                .setMessage("数据解析异常")  
		                .setPositiveButton("确定", null)
		                .show();
	                	return;
	                }   
				  
				  //无数据
	                if (danger == null || danger.getResultCode().equals("0")||danger.getDangerInfoCount()==0) {
		            	new AlertDialog.Builder(DormDangerSearchActivity.this)    
		                .setTitle("提示")  
		                .setMessage("没有相关的隐患信息！")  
		                .setPositiveButton("确定",null)
		                .show(); 
	    				return;
	                }
	                Intent intent = new Intent();
	                intent.putExtra("JsonDanger", danger.toJson());
	                intent.setClass(DormDangerSearchActivity.this,DormDangerSearchInfoActivity.class);
	                startActivity(intent);  
	                
			}
			
		});
	}
		
	 
}
