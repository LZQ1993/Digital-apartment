package com.chinawit.scloud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.chinawit.scloud.app.AppInfo;


public class StartupActivity extends Activity {

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        ((TextView) findViewById(R.id.startup_tv_version)).setText(AppInfo.getVersionShow());

        //延时线程
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                }
                //判断是否为首次登陆
                Intent intent = new Intent();
                if (AppInfo.isNewVersion()) { 
                    intent.setClass(StartupActivity.this,IntroActivity.class);
                    intent.putExtra("goto", MainActivity.class.getName());
                    AppInfo.markCurrentVersion(); //标记当前版本
                } else {
                     
                	intent.setClass(StartupActivity.this,MainActivity.class);
                }
                
                startActivity(intent);
                finish();
            }
        }.start();
  
    }
   
    /**
     * 返回键按下
     */
    @Override
    public void onBackPressed() {
    	
    }
   

}
