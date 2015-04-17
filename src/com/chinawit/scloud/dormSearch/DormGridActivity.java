package com.chinawit.scloud.dormSearch;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormGridActivity extends NavBarActivity  {

	/** 
	 * onCreate 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_grid);
		initNavBar("寝室列表", "<返回", null);
		initWidget();
	}

	/**
	 * 初始化控件
	 */
	private void initWidget() {
		//取出数据
		final String[] strsDorm = getIntent().getStringArrayExtra("strsDorm");
		//提示
		Toast.makeText(DormGridActivity.this,"共 "+strsDorm.length+" 个寝室", Toast.LENGTH_SHORT).show();
		//绑定控件
		GridView gridView = (GridView) findViewById(R.id.dorm_grid_gridView);
		//适配器
		gridView.setAdapter(new BaseAdapter() {
			//获取view
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView tv = new TextView(DormGridActivity.this);
				tv.setText(strsDorm[position]);
				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(25);
				tv.setTextColor(0xFF000000);
				tv.setBackgroundColor(0xFFFFFFFF);
				tv.setPadding(0, 14, 0, 14);
				return tv;
			}
			//获取viewId
			@Override
			public long getItemId(int position) {
				return position;
			}
			//获取位置
			@Override
			public Object getItem(int position) {
				return position;
			}
			//获取个数
			@Override
			public int getCount() {
				return strsDorm.length;
			}
		});
		//监听器
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				getDormPlateByDormNumTask(strsDorm[position]);
			}
		});
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
            	new AlertDialog.Builder(DormGridActivity.this)    
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
	            	new AlertDialog.Builder(DormGridActivity.this)    
	                .setTitle("错误")  
	                .setMessage("数据解析异常")  
	                .setPositiveButton("确定", null)
	                .show();
                	return;
                }
                //数据为空
    			if (dormInfo == null ||dormInfo.getDormNum() == null || dormInfo.getDormNum().equals("")) {
	            	new AlertDialog.Builder(DormGridActivity.this)    
	                .setTitle("提示")  
	                .setMessage("未获取到寝室信息\n或该寝室不存在！")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
    			}		
    			//权限充足
                Intent intent = new Intent();
                intent.putExtra("jsonDormPlate",dormInfo.toJson());
                intent.setClass(DormGridActivity.this, DormInfoActivity.class);
                startActivity(intent);
			}
		});

	}
}

