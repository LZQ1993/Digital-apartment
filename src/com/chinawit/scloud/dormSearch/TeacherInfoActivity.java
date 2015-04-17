package com.chinawit.scloud.dormSearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.Teacher;
import com.chinawit.scloud.bean.TeacherCode;

public class TeacherInfoActivity extends NavBarActivity {

	
	 private TextView teacher_tvBase;
	 private TextView teacher_tvTel;
	 private TextView teacher_tvClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_info);
		initNavBar("老师详细信息", "<返回", null);
        teacher_tvBase = (TextView) findViewById(R.id.teacher_info_tv_base);
        teacher_tvClass = (TextView) findViewById(R.id.teacher_info_tv_class);
        teacher_tvTel = (TextView) findViewById(R.id.teacher_info_tv_tel);

        
        try {
        	TeacherCode teacherCode = TeacherCode.fromJson(getIntent().getStringExtra("jsonTeacher"));
            if (teacherCode == null) {
                new AlertDialog.Builder(this)    
                .setTitle("错误")  
                .setMessage("老师信息为空，请重试！")  
                .setPositiveButton("确定",new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show(); 
            }else {
            	
            	Teacher teacher = teacherCode.getTeacher().get(0);
                //基本信息
                StringBuffer sbBase = new StringBuffer();
                sbBase.append("教师编号:").append(teacher.getTeacherID()).append("\n");
                sbBase.append("教师姓名:").append(teacher.getTeacherName()).append("\n");
                if(teacher.getPost()==null)
                {
                	sbBase.append("教师职务:").append("无").append("\n");
                }else{
                	sbBase.append("教师职务:").append(teacher.getPost()).append("\n");
                }
                if(teacher.getCertification()==null){
                	sbBase.append("教师职称:").append("无").append("\n");
                }else{
                	sbBase.append("教师职称:").append(teacher.getCertification()).append("\n");
                }
                if(teacher.getUnit()==null){
                	 sbBase.append("所在单位:").append("无").append("\n");
                }else{
                	 sbBase.append("所在单位:").append(teacher.getUnit()).append("\n");
                }
               if(teacher.getIsInstructure()==null){
            	   sbBase.append("专兼职辅导员:").append("  ").append("无").append("\n");
               }else{
            	   sbBase.append("专兼职辅导员:").append("  ").append(teacher.getIsInstructure()).append("\n");
               }
                
               
               //联系方式
               StringBuffer sbTel = new StringBuffer();         
               sbTel.append("联系电话:").append(teacher.getTel()).append("\n");
                //班级信息
                StringBuffer sbClass = new StringBuffer();
                String[] classes = teacher.getClasses();
                int j = teacher.getClasses().length;
                sbClass.append("所带班级:").append("\n");
                for(int i=0;i<j;i++){
                	sbClass.append("		");
                	sbClass.append(classes[i]).append("\n");
                
                }
              
                //赋值
                teacher_tvBase.setText(sbBase.toString());
                teacher_tvClass.setText(sbClass.toString());      
                teacher_tvTel.setText(sbTel.toString());
            }        
        } catch (Exception e) {
            new AlertDialog.Builder(this)    
            .setTitle("错误")  
            .setMessage("数据解析异常")  
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

}
