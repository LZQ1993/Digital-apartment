package com.chinawit.scloud.dormScore;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.DormScore;
import com.chinawit.scloud.bean.PrimaryResult;
import com.chinawit.scloud.bean.PrimaryScore;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class DormScoreInfoActivity extends NavBarActivity{
	private TextView tv_base;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_score_info);
		initNavBar("评比信息", "<返回", null);
		tv_base = (TextView) findViewById(R.id.dorm_score_info_tv);
		StringBuffer sbBase = new StringBuffer(); 
        sbBase.append("学期：").append(getIntent().getStringExtra("Term")).append("		");
        sbBase.append("周次：").append(getIntent().getStringExtra("Week")).append("\n");
        sbBase.append("级别：").append(getIntent().getStringExtra("CheckLevel")).append("\n");
        sbBase.append("类型：").append(getIntent().getStringExtra("CheckType"));
       
        tv_base.setText(sbBase.toString());
        
      //获取信息
        PrimaryScore primaryScore = null;
		try {
			primaryScore = PrimaryScore.fromJson(getIntent().getStringExtra("josnScore"));
			if (primaryScore.getResultCode().equals("0")) { //无人
				new AlertDialog.Builder(this)    
		        .setTitle("提示")
		        .setMessage("该寝室暂时无考核信息。")  
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
      //初始化ListView
		ListView lvScore = (ListView) findViewById(R.id.dorm_score_info_lv);
		lvScore.setAdapter(new ListViewAdapter(this,primaryScore));
		lvScore.setOnItemClickListener(new ListViewItemClickListener(primaryScore));	
	}

	
	/**
     * 适配器内部类
     */
    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, PrimaryScore primaryScore) {
            //初始化一个布局加载器
            LayoutInflater inflater = LayoutInflater.from(context);
            //创建一个自相布局数组
            itemViews = new ArrayList<View>();
            for (int n=0; n<primaryScore.getPrimaryResultCount(); n++) {
            	
            	PrimaryResult primaryResult = primaryScore.getPrimaryResult().get(n);
                View itemView = inflater.inflate(R.layout.activity_dorm_score_info_item,null);
                //初始化子布局
                TextView tvWeek = (TextView) itemView.findViewById(R.id.dorm_score_info_lv_item_tv_week);
                tvWeek.setText(primaryResult.getWeek());  
                TextView tvAllScore = (TextView) itemView.findViewById(R.id.dorm_score_info_lv_item_tv_allScore);
                tvAllScore.setText(primaryResult.getScore());                  
                //添加到布局中
                itemViews.add(itemView);
            }
            
        }

        @Override
        public int getCount() {
            return itemViews.size();
        }

        @Override
        public Object getItem(int position) {
            return itemViews.get(position);
        }

        @Override
        public long getItemId(int position) {
            return itemViews.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return itemViews.get(position);
        }

    }
    /**
     * 监听器内部类
     */
    private class ListViewItemClickListener implements OnItemClickListener {

    	private  PrimaryScore primaryScore;
    	
		public ListViewItemClickListener( PrimaryScore primaryScore) {
			this.primaryScore =primaryScore;
		}

		@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			 PrimaryResult primaryResult = primaryScore.getPrimaryResult().get(position);
			 getResultByCheckId(primaryResult.getCheckId());
        }

    }
    
    /**
     * 获取详细考核信息任务
     */
    private void getResultByCheckId(int CheckId) {
        showProgressDialog();
        String url = NetworkInfo.getServiceUrl() + "GetResultByCheckId.ashx";
        RequestParams params = new RequestParams();   
        params.put("CheckId", CheckId+"");
        if(getIntent().getStringExtra("CheckType").equals("普查")){
        	params.put("CheckType","0");
        }
        if(getIntent().getStringExtra("CheckType").equals("复查")){
        	params.put("CheckType", "1");
        }
       if(getIntent().getStringExtra("CheckType").equals("抽查")){
    	   params.put("CheckType", "2");
       }
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(Throwable error) {
				dismissProgressDialog();
                new AlertDialog.Builder(DormScoreInfoActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！"+error.toString())  
                .setPositiveButton("确定",null)
                .show();
                return;
			}

			@Override
			public void onSuccess(String content) {
				dismissProgressDialog();
				DormScore dormScore = null;
                try {
                	dormScore = DormScore.fromJson(content);
                } catch(Exception e) {
                    new AlertDialog.Builder(DormScoreInfoActivity.this)    
                    .setTitle("错误")  
                    .setMessage("数据解析异常")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
                if (dormScore==null) {
                    new AlertDialog.Builder(DormScoreInfoActivity.this)    
                    .setTitle("提示")  
                    .setMessage("没有寝室考核成绩数据!")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("scoreResult",dormScore.toJson());
                intent.setClass(DormScoreInfoActivity.this, DormScoreShowActivity.class);
                startActivity(intent);
			}
        	
        });
    }


}
