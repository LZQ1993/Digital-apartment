package com.chinawit.scloud.dormSearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.Student;

public class StudentCallActivity extends NavBarActivity {
	
	private Button btnYd;
	private Button btnLt;
	private Button btnDx;
	private Button btnFather;
	private Button btnMother;
	private Button btnTeacher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_call);
		initNavBar("联系方式", "<返回", null);
		
		try {
			Student student = Student.fromJson(getIntent().getStringExtra("jsonStudent"));
			if (student == null) {
	    		new AlertDialog.Builder(this)    
	            .setTitle("错误")  
	            .setMessage("学生信息为空，请重试！")  
	            .setPositiveButton("确定",new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
	            })
	            .setCancelable(false)
	            .show(); 
			} else {
				//初始化联系方式
				initNavBar(student.getStudentName() + "的联系方式","返回",null);
				btnYd = (Button) findViewById(R.id.student_call_btn_yd);
				btnLt = (Button) findViewById(R.id.student_call_btn_lt);
				btnDx = (Button) findViewById(R.id.student_call_btn_dx);
				btnFather = (Button) findViewById(R.id.student_call_btn_father);
				btnMother = (Button) findViewById(R.id.student_call_btn_mother);
				btnTeacher = (Button) findViewById(R.id.student_call_btn_teacher);
				if(student.getMobileTel()==null){
					btnYd.setText("无");
                }
                else{
                	btnYd.setText(student.getMobileTel());  
                }   
                if(student.getUniComeTel()==null){
                	btnLt.setText("无");
                }
                else{
                	btnLt.setText(student.getUniComeTel());
                } 
                if(student.getTeleComTel()==null){
                	btnDx.setText("无");
                }
                else{
                	btnDx.setText(student.getTeleComTel());     
                } 
                if(student.getFatherTel()==null){
                	btnFather.setText("无");
                }
                else{
                	btnFather.setText(student.getFatherTel());     
                } 
                if(student.getMotherTel()==null){
                	btnMother.setText("无");
                }
                else{
                	btnMother.setText(student.getMotherTel());     
                } 
                if(student.getTeacherTel()==null){
                	btnTeacher.setText("无");  
                }
                else{
                	btnTeacher.setText(student.getTeacherTel());    
                } 
				
			}		
		} catch (Exception e) {
    		new AlertDialog.Builder(this)    
            .setTitle("错误")  
            .setMessage("学生详细信息读取失败\n请您与管理员联系！")  
            .setPositiveButton("确定",new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
            })
            .setCancelable(false)
            .show(); 
		}
	}
	
	/**
	 * 拨打电话选项
	 * @param view
	 */
	public void onPhoneNumClick(View view) {
		Button btn = (Button) view;
		final String num = btn.getText().toString();
		if (num == null || num.equals("无")) {
        	new AlertDialog.Builder(this)    
            .setTitle("提示")  
            .setMessage("电话号码为空。")  
            .setPositiveButton("确定",null)
            .show();
			return;
		}
		//对话框
		new AlertDialog.Builder(this)
        .setTitle("号码为：" + num + "\n请选择您要进行的操作...")
        .setItems(new String[] {"打电话", "发短信"}, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (which == 0) { //打电话
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));  
			        startActivity(intent);
				}
				else if (which == 1) { //发短信
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("smsto:"+num));
					startActivity(intent);
				}
			}
		})
        .setNegativeButton("取消", null)
        .show();
	}

}

