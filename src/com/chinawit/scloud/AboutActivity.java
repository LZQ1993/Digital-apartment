package com.chinawit.scloud;

import com.chinawit.scloud.app.AppInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class AboutActivity extends NavBarActivity {

    private final String str_content = ""
            + "开发小组：华慧科技\n"
            + "官方网站：\nhttp://www.china-wit.com/scloud\n"
            + "";
    private final String str_footer = ""
            + "华慧科技    版权所有\n"
            + "Copyright © 2013-2014 ChinaWit.\n"
            + "All Rights Reserved.\n"
            + "";

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initNavBar("关于","<返回","更新日志");
        TextView tvVersion = (TextView) findViewById(R.id.about_tv_version);
        tvVersion.setText(AppInfo.getVersionShow());
        TextView tvContent = (TextView) findViewById(R.id.about_tv_content);
        tvContent.setText(str_content);
        TextView tvFooter  = (TextView) findViewById(R.id.about_tv_footer);
        tvFooter.setText(str_footer);
    }

    /**
     * 导航右键
     */
    @Override
    public void onNavBarRightButtonClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, UpdateLogActivity.class);
        startActivity(intent);
    }

    /**
     * 打开欢迎页面
     */
    public void onClickBtnWelcome(View view) {
        Intent intent = new Intent();
        intent.setClass(this, IntroActivity.class);
        startActivity(intent);
    }

    /**
     * 打开问题反馈页面
     */
    public void onClickBtnBugFeedback(View view) {
        Intent intent = new Intent();
        intent.setClass(this, BugFeedbackActivity.class);
        startActivity(intent);
    }

}
