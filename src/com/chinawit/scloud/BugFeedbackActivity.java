package com.chinawit.scloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class BugFeedbackActivity extends NavBarActivity {

    private EditText edtBug;
    private EditText edtEmail;

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_feedback);
        initNavBar("问题反馈", "<返回", null);
        edtBug = (EditText) findViewById(R.id.bug_feedback_edt_bug);
        edtEmail = (EditText) findViewById(R.id.bug_feedback_edt_email);
    }

    /**
     * 提交条件
     */
    public void onSubmit(View view) {
        if(this.edtBug.getText().toString().equals("")) {
            new AlertDialog.Builder(this)    
            .setTitle("提示")  
            .setMessage("亲，您还未填写任何反馈信息！")  
            .setPositiveButton("确定", null) 
            .show(); 
        } else {
            // TODO
            //获取信息
            edtEmail.getText();
            // TODO
            new AlertDialog.Builder(this)    
            .setTitle("提示")  
            .setMessage("您的反馈已提交，感谢您的支持！")  
            .setPositiveButton("确定",new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setCancelable(false)
            .show(); 
        }
    }

}
