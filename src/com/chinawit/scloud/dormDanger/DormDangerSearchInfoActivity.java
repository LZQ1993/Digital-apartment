package com.chinawit.scloud.dormDanger;

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
import com.chinawit.scloud.bean.Danger;
import com.chinawit.scloud.bean.DangerInfo;


public class DormDangerSearchInfoActivity extends NavBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_danger_search_info);
		initNavBar("隐患信息","<返回", null);
		initData();
	
	}
    private void initData(){
		 //获取信息
        Danger danger = null;
		try {
			danger = Danger.fromJson(getIntent().getStringExtra("JsonDanger"));
			if (danger == null || danger.getResultCode().equals("0")||danger.getDangerInfoCount()==0) { //无人
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
      //初始化ListView
		ListView lvDanger = (ListView) findViewById(R.id.dorm_danger_search_info_lv);
		lvDanger.setAdapter(new ListViewAdapter(this,danger));
		lvDanger.setOnItemClickListener(new ListViewItemClickListener(danger));
    }
	
	/**
     * 适配器内部类
     */
    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, Danger danger) {
            //初始化一个布局加载器
            LayoutInflater inflater = LayoutInflater.from(context);
            //创建一个自相布局数组
            itemViews = new ArrayList<View>();
            for (int n=0; n<danger.getDangerInfoCount(); n++) {
            	DangerInfo DangerInfo = danger.getResult().get(n);
                View itemView = inflater.inflate(R.layout.activity_dorm_danger_search_info_item,null);
                //初始化子布局
               
               
                TextView tvDescroption = (TextView) itemView.findViewById(R.id.dorm_danger_search_info_lv_item_tv_descroption);
                tvDescroption.setText(DangerInfo.getDescription());   
                
                TextView tvState = (TextView) itemView.findViewById(R.id.dorm_danger_search_info_lv_item_tv_state);
               

                if(DangerInfo.getState().equals("0")){
                	tvState.setText("未处理");

                }
                if(DangerInfo.getState().equals("1")){
                	  tvState.setText("已处理");
                }
                TextView tvSubmitTime = (TextView) itemView.findViewById(R.id.dorm_danger_search_info_lv_item_tv_submitTime);
                tvSubmitTime.setText(DangerInfo.getSubmitTime()); 
          
 
                
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

    	private  Danger danger;
    	
		public ListViewItemClickListener( Danger danger) {
			this.danger =danger;
		}

		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			 DangerInfo dangerInfo = danger.getResult().get(position);
			 Intent intent = new Intent();  
			 intent.putExtra("json", dangerInfo.toJson());
             intent.setClass(DormDangerSearchInfoActivity.this, DormDangerSearchShowActivity.class);
             startActivity(intent);
        }

    }
    
    
}
	
