package com.chinawit.scloud.dormNotice;

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
import com.chinawit.scloud.bean.Notice;
import com.chinawit.scloud.bean.NoticeInfo;


public class DormNoticeSearchInfoActivity extends NavBarActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_notice_search_info);
		initNavBar("通知查询","<返回", null);
		 //获取信息
		 Notice notice = null;
		try {
			notice = Notice.fromJson(getIntent().getStringExtra("JsonNotice"));
			if (notice==null) { //无人
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
      //初始化ListView
		ListView lvNotice = (ListView) findViewById(R.id.dorm_notice_search_info_lv);
		lvNotice.setAdapter(new ListViewAdapter(this,notice));
		lvNotice.setOnItemClickListener(new ListViewItemClickListener(notice));	
	}

	
	/**
     * 适配器内部类
     */
    private class ListViewAdapter extends BaseAdapter {

        private List<View> itemViews;

        public ListViewAdapter(Context context, Notice notice) {
            //初始化一个布局加载器
            LayoutInflater inflater = LayoutInflater.from(context);
            //创建一个自相布局数组
            itemViews = new ArrayList<View>();
            for (int n=0; n<notice.getNoticeInfoCount(); n++) {
            	NoticeInfo noticeInfo = notice.getNoticeInfo().get(n);
                View itemView = inflater.inflate(R.layout.activity_dorm_notice_search_info_item,null);
                //初始化子布局
                TextView tvNoticeCollege = (TextView) itemView.findViewById(R.id.dorm_notice_search_info_lv_item_tv_college);
                tvNoticeCollege.setText(noticeInfo.getCollege());   
                
                TextView tvNoticeContent = (TextView) itemView.findViewById(R.id.dorm_notice_search_info_lv_item_tv_descroption);
                tvNoticeContent.setText(noticeInfo.getNoticeContent());   
               
                TextView tvState = (TextView) itemView.findViewById(R.id.dorm_notice_search_info_lv_item_tv_state);
                if(noticeInfo.getState().equals("1")){
              	  tvState.setText("有效通知");
                }else{
                	  tvState.setText("无效通知");
                }
                TextView tvEndTime = (TextView) itemView.findViewById(R.id.dorm_notice_search_info_lv_item_tv_endTime);
                tvEndTime.setText(noticeInfo.getRemindTime()); 
                
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

    	private  Notice notice;
    	
		public ListViewItemClickListener(Notice notice) {
			this.notice =notice;
		}

		@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			NoticeInfo noticeInfo = notice.getNoticeInfo().get(position);
			 Intent intent = new Intent();
             intent.putExtra("noticeResult",noticeInfo.toJson());
             intent.setClass(DormNoticeSearchInfoActivity.this, DormNoticeSearchInfoShowActivity.class);
             startActivity(intent);
        }

    }  
    
}
	
