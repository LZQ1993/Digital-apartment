package com.chinawit.scloud.dormSearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.bean.Student;

public class StudentInfoActivity extends NavBarActivity {

    private TextView tvBase;
    private TextView tvCon;
    private TextView tvHome;
    private TextView tvClass;
    private TextView tvDorm;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        initNavBar("学生详细信息", "<返回", "拨号");
        tvBase = (TextView) findViewById(R.id.student_info_tv_base);
        tvCon = (TextView) findViewById(R.id.student_info_tv_con);
        tvHome = (TextView) findViewById(R.id.student_info_tv_home);
        tvClass = (TextView) findViewById(R.id.student_info_tv_class);
        tvDorm = (TextView) findViewById(R.id.student_info_tv_dorm);
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
                //基本信息
                StringBuffer sbBase = new StringBuffer();
                sbBase.append("姓名：").append(student.getStudentName()).append("\n");
                sbBase.append("性别：").append(student.getSex()).append("\n");
                sbBase.append("民族：").append(student.getNation()).append("\n");
                sbBase.append("学号：").append(student.getStudentNum()).append("\n");
                sbBase.append("身份证号：\n").append(student.getIdCardNum());
                //联系方式
                StringBuffer sbCon = new StringBuffer();
                if(student.getMobileTel()==null){
                	sbCon.append("移动电话号：").append("无").append("\n");
                }
                else{
                	sbCon.append("移动电话号：").append(student.getMobileTel()).append("\n");  
                }   
                if(student.getUniComeTel()==null){
                	sbCon.append("联通手机号：").append("无").append("\n");
                }
                else{
                	sbCon.append("联通手机号：").append(student.getUniComeTel()).append("\n");
                } 
                if(student.getTeleComTel()==null){
                	sbCon.append("电信手机号：").append("无").append("\n");
                }
                else{
                	sbCon.append("电信手机号：").append(student.getTeleComTel()).append("\n");     
                } 
          
                 
                //家庭信息
                StringBuffer sbHome = new StringBuffer();
                sbHome.append("父亲姓名：").append(student.getFatherName()).append("\n");
                if(student.getFatherTel()==null){
                	sbCon.append("父亲电话号：").append("无").append("\n");
                }
                else{
                	sbHome.append("父亲电话号：").append(student.getFatherTel()).append("\n");     
                } 
                
                sbHome.append("母亲姓名：").append(student.getMotherName()).append("\n");
                if(student.getMotherTel()==null){
                	sbCon.append("母亲电话号：").append("无").append("\n");
                }
                else{
                	sbHome.append("母亲电话号：").append(student.getMotherTel()).append("\n");     
                } 
                
                sbHome.append("家庭住址：\n").append(student.getAddress());
                //班级信息
                StringBuffer sbClass = new StringBuffer();
                sbClass.append("学院：").append(student.getCollege()).append("\n");
                sbClass.append("专业：").append(student.getProfessional()).append("\n");
                sbClass.append("班级：").append(student.getAbbreviation()).append("\n");
                sbClass.append("班主任姓名：").append(student.getTeacherName()).append("\n");
                sbClass.append("班主任电话号：").append(student.getTeacherTel());
                //公寓信息
                StringBuffer sbDorm = new StringBuffer();
              
                sbDorm.append("寝室号：").append(student.getStudentDormNum()).append("\n");
               
                //赋值
                tvBase.setText(sbBase.toString());
                tvCon.setText(sbCon.toString());
                tvHome.setText(sbHome.toString());
                tvClass.setText(sbClass.toString());
                tvDorm.setText(sbDorm.toString());
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

    /**
     * 拨打电话
     */
    @Override
    public void onNavBarRightButtonClick(View view) {
        Intent intent = getIntent();
        intent.setClass(this, StudentCallActivity.class);
        startActivity(intent);
    }

}
