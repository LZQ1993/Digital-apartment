package com.chinawit.scloud.dormNotice;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.Notice;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormNoticeSearchActivity extends NavBarActivity{
	private Spinner spnType;
	private Spinner spnState;
	private EditText edtKeyWords;
    private Button pickDate = null;
    private EditText showDate = null;
    
    private static final int SHOW_DATAPICK = 0; 
    private static final int DATE_DIALOG_ID = 1;  
    
    private int mYear;  
    private int mMonth;
    private int mDay; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dorm_notice_search);
		initNavBar("通知查询", "<返回", null);
		initializeViews();  
		
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);  
        mMonth = c.get(Calendar.MONTH); 
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
		
	}
	
	 /**
     * 初始化控件和UI视图
     */
    private void initializeViews(){
    	spnType = (Spinner) findViewById(R.id.dorm_notice_search_type_spn);
		spnState = (Spinner) findViewById(R.id.dorm_notice_search_state_spn);
		edtKeyWords = (EditText) findViewById(R.id.dorm_notice_search_keyWords_edt);
		showDate = (EditText) findViewById(R.id.showdate);  
		pickDate = (Button) findViewById(R.id.btnDate); 
		pickDate.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
               Message msg = new Message(); 
               if (pickDate.equals((Button) v)) {  
                  msg.what = DormNoticeSearchActivity.SHOW_DATAPICK;  
               }  
               DormNoticeSearchActivity.this.dateandtimeHandler.sendMessage(msg); 
            }
        });
    }
    /**
     * 设置日期
     */
    private void setDateTime(){
       final Calendar c = Calendar.getInstance();  
        
       mYear = c.get(Calendar.YEAR);  
       mMonth = c.get(Calendar.MONTH);  
       mDay = c.get(Calendar.DAY_OF_MONTH); 
   
       updateDateDisplay(); 
    }
     
    /**
     * 更新日期显示
     */
    private void updateDateDisplay(){
       showDate.setText(new StringBuilder().append(mYear).append("-")
               .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
               .append((mDay < 10) ? "0" + mDay : mDay)); 
    }
    /** 
     * 日期控件的事件 
     */ 
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
   
       public void onDateSet(DatePicker view, int year, int monthOfYear,  
              int dayOfMonth) {  
           mYear = year;  
           mMonth = monthOfYear;  
           mDay = dayOfMonth;  
 
           updateDateDisplay();
       }  
    }; 
    @Override 
    protected Dialog onCreateDialog(int id) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
	                  mDay);
	       }
	            
	       return null;  
	    }  
	   
	    @Override 
	    protected void onPrepareDialog(int id, Dialog dialog) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
	           break;
	       }
	    }  
	   
	    /** 
	     * 处理日期和时间控件的Handler 
	     */ 
	    Handler dateandtimeHandler = new Handler() {
		@Override 
	       public void handleMessage(Message msg) {  
	           switch (msg.what) {  
	           case DormNoticeSearchActivity.SHOW_DATAPICK:  
	               showDialog(DATE_DIALOG_ID);  
	               break; 
	           }  
	       }  
	   
	    }; 
	/*
	 * 通知查询
	 */
	 public void noticeSearch(View view) {
		 if(spnType.getSelectedItemPosition()==0){
			 new AlertDialog.Builder(DormNoticeSearchActivity.this)    
	            .setTitle("提示")  
	            .setMessage("您必须填写相应的查询")  
	            .setPositiveButton("确定", null)
	            .show(); 
		 }else{
			 getNoticeInfo(); 
 
		 }	  
		
	 }

	private void getNoticeInfo() {
		showProgressDialog();
		String url = NetworkInfo.getServiceUrl() + "getNoticeList.ashx";
		RequestParams params = new RequestParams();
		params.put("NoticeType",spnType.getSelectedItem().toString());
		params.put("NoticeState",spnState.getSelectedItem().toString());
		params.put("keyword", edtKeyWords.getText().toString());
		params.put("NoticeValidTime",showDate.getText().toString());
		HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error) {
				dismissProgressDialog();
	            new AlertDialog.Builder(DormNoticeSearchActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败，请检查网络！")  
	                .setPositiveButton("确定",null)
	                .show();
			}

			@Override
			public void onSuccess(String content) {
				  dismissProgressDialog();
				  Notice notice = null;
				  try { 
					  notice = Notice.fromJson(content);
	                } catch (Exception e) {
		            	new AlertDialog.Builder(DormNoticeSearchActivity.this)    
		                .setTitle("错误")  
		                .setMessage("服务器返回：" + content)  
		                .setPositiveButton("确定", null)
		                .show();
	                	return;
	                }   
				  
				  //无数据
	                if (notice == null || notice.getResultCode().equals("0")) {
		            	new AlertDialog.Builder(DormNoticeSearchActivity.this)    
		                .setTitle("提示")  
		                .setMessage("没有相关的通知信息！")  
		                .setPositiveButton("确定",null)
		                .show(); 
	    				return;
	                }
	                Intent intent = new Intent();
	                intent.putExtra("JsonNotice", notice.toJson());
	                intent.setClass(DormNoticeSearchActivity.this,DormNoticeSearchInfoActivity.class);
	                startActivity(intent);  
	                
			}
			
		});
	}
		
	 
}
