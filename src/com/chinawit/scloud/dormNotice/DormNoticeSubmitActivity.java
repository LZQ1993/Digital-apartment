package com.chinawit.scloud.dormNotice;


import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.app.UserInfo;
import com.chinawit.scloud.bean.SubmitNotice;
import com.http.HttpUtil;
import com.json.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DormNoticeSubmitActivity extends NavBarActivity{
	    private EditText showDate = null;
	    private Button pickDate = null;
	    private EditText showTime = null;
	    private Button pickTime = null;
	    private Spinner noticeType = null;
	    private EditText edtContent = null;
	     
	    private static final int SHOW_DATAPICK = 0; 
	    private static final int DATE_DIALOG_ID = 1;  
	    private static final int SHOW_TIMEPICK = 2;
	    private static final int TIME_DIALOG_ID = 3;
	     
	    private int mYear;  
	    private int mMonth;
	    private int mDay; 
	    private int mHour;
	    private int mMinute;
	    
	    private SubmitNotice submitNotice;
	     
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_dorm_notice_submit);
	        initNavBar("通知发布", "<返回", null);
	        initializeViews();
	         
	        final Calendar c = Calendar.getInstance();
	        mYear = c.get(Calendar.YEAR);  
	        mMonth = c.get(Calendar.MONTH);  
	        mDay = c.get(Calendar.DAY_OF_MONTH);
	         
	        mHour = c.get(Calendar.HOUR_OF_DAY);
	        mMinute = c.get(Calendar.MINUTE);
	         
	        setDateTime(); 
	        setTimeOfDay();
	    }
	     
	    /**
	     * 初始化控件和UI视图
	     */
	    private void initializeViews(){
	        showDate = (EditText) findViewById(R.id.showdate);  
	        pickDate = (Button) findViewById(R.id.btnDate); 
	        showTime = (EditText)findViewById(R.id.showtime);
	        pickTime = (Button)findViewById(R.id.btnTime);
	        noticeType = (Spinner) findViewById(R.id.dorm_notice_submit_type_spn);
	        edtContent = (EditText)findViewById(R.id.dorm_notice_submit_edtContent);
	        pickDate.setOnClickListener(new View.OnClickListener() {
	             
	            @Override
	            public void onClick(View v) {
	               Message msg = new Message(); 
	               if (pickDate.equals((Button) v)) {  
	                  msg.what = DormNoticeSubmitActivity.SHOW_DATAPICK;  
	               }  
	               DormNoticeSubmitActivity.this.dateandtimeHandler.sendMessage(msg); 
	            }
	        });
	         
	        pickTime.setOnClickListener(new View.OnClickListener() {
	             
	            @Override
	            public void onClick(View v) {
	               Message msg = new Message(); 
	               if (pickTime.equals((Button) v)) {  
	                  msg.what = DormNoticeSubmitActivity.SHOW_TIMEPICK;  
	               }  
	               DormNoticeSubmitActivity.this.dateandtimeHandler.sendMessage(msg); 
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
	     
	    /**
	     * 设置时间
	     */
	    private void setTimeOfDay(){
	       final Calendar c = Calendar.getInstance(); 
	       mHour = c.get(Calendar.HOUR_OF_DAY);
	       mMinute = c.get(Calendar.MINUTE);
	       updateTimeDisplay();
	    }
	     
	    /**
	     * 更新时间显示
	     */
	    private void updateTimeDisplay(){
	       showTime.setText(new StringBuilder().append(mHour).append(":")
	               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
	    }
	     
	    /**
	     * 时间控件事件
	     */
	    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
	         
	        @Override
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	            mHour = hourOfDay;
	            mMinute = minute;
	             
	            updateTimeDisplay();
	        }
	    };
	     
	    @Override 
	    protected Dialog onCreateDialog(int id) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
	                  mDay);
	       case TIME_DIALOG_ID:
	           return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
	       }
	            
	       return null;  
	    }  
	   
	    @Override 
	    protected void onPrepareDialog(int id, Dialog dialog) {  
	       switch (id) {  
	       case DATE_DIALOG_ID:  
	           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
	           break;
	       case TIME_DIALOG_ID:
	           ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
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
	           case DormNoticeSubmitActivity.SHOW_DATAPICK:  
	               showDialog(DATE_DIALOG_ID);  
	               break; 
	           case DormNoticeSubmitActivity.SHOW_TIMEPICK:
	               showDialog(TIME_DIALOG_ID);
	               break;
	           }  
	       }  
	   
	    }; 
	    
	    
	    public void noticeSubmit(View view) {
	/*	    //用户未登录
			if (UserInfo.getUser() == null) {
				new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
	             .setTitle("提示")  
	             .setMessage("该功能模块，仅特定权限账户才能使用。您未登录账户。\n您确定要登录账户？")  
	             .setPositiveButton("确定",new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent();
							intent.setClass(DormNoticeSubmitActivity.this, LoginActivity.class);
							startActivity(intent);
						}
					})
	             .setNegativeButton("取消",null)
	             .show();
					return;
				}
			//权限不足
			if (UserInfo.getUser().getPower()<1) {
					new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
	                .setTitle("提示")  
	                .setMessage("权限不足！\n您可以尝试与管理员联系，以便获得更多的授权。")  
	                .setPositiveButton("确定",null)
	                .show();
	            	return;
			}*/
			if(noticeType.getSelectedItemPosition()==0||edtContent.getText().toString().equals("")){
						 new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
				            .setTitle("提示")  
				            .setMessage("您必须填写相应的通知内容")  
				            .setPositiveButton("确定", null)
				            .show(); 
						    return;
					 }
			submitNotice = new SubmitNotice();
			submitNotice.setNoticeType(noticeType.getSelectedItem().toString());
			submitNotice.setNoticeContent(edtContent.getText().toString());
			submitNotice.setNoticeValidTime(showDate.getText().toString()+showTime.getText().toString());
			submitNotice.setSubmitPeopleID(UserInfo.getUser().getId()+"");
			noticeSubmit();
	 }

	private void noticeSubmit() {
		  showProgressDialog(); 
		  String url = NetworkInfo.getServiceUrl() + "uploadNotice.ashx";
		  RequestParams params = new RequestParams();
		  params.put("json", submitNotice.toJson());
		  HttpUtil.post(url, params, new AsyncHttpResponseHandler() {
	            @Override
	            public void onFailure(Throwable error) {
	                dismissProgressDialog();
	                new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
	                .setTitle("错误")  
	                .setMessage("网络访问失败！\n请您在检查网络情况后重试。")  
	                .setPositiveButton("确定",null)
	                .show();
	            }

	            @Override
	            public void onSuccess(String content) {
	                dismissProgressDialog();
	               // if (content.equals("上传成功")) {
	                String  result [] = null;
	                try {
	                	result =JsonUtil.fromJson(content, String[].class);
	                } catch (Exception e) {
		            	new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
		                .setTitle("错误")  
		                .setMessage("服务器返回：" + content)  
		                .setPositiveButton("确定", null)
		                .show();
	                	return;
	                }
	                if (result[0].equals("1")) {
	                    new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
	                    .setTitle("提示")  
	                    .setMessage("发布成功！")  
	                    .setPositiveButton("确定", new OnClickListener() {
	                        @Override
	                        public void onClick(DialogInterface arg0, int arg1) {
	                            finish();
	                        }
	                    })
	                    .setCancelable(false)
	                    .show();
	              
	                } else {
	                    new AlertDialog.Builder(DormNoticeSubmitActivity.this)    
	                    .setTitle("错误")  
	                    .setMessage("服务器返回：" + content)  
	                    .setPositiveButton("确定", null)
	                    .show();
	                }
	            }
	        });
	    }

	     
	}
