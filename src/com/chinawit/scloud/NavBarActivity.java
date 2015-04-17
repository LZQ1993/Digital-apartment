package com.chinawit.scloud;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public abstract class NavBarActivity extends Activity {

    public final static int RESULT_OK = Activity.RESULT_OK;
    public final static int RESULT_ERROR = Activity.RESULT_FIRST_USER;
    public final static int RESULT_CANCELED = Activity.RESULT_CANCELED;
    public final static int REQUEST_DEFAULT = 0;

    private ProgressDialog progressDialog;
    
    private TextView tvTitle;
    private Button btnLeft;  
    private Button btnRight;

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);  
        progressDialog.setMessage("访问网络中，请稍后...");
        progressDialog.setCancelable(false);
    }
    
    /**
     * 显示进度条
     */
    protected void showProgressDialog() {
        progressDialog.show();
    }
    
    /**
     * 隐藏进度条
     */
    protected void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    /**
     * 初始化导航栏
     */
    protected void initNavBar(String title,String left,String right) {
        tvTitle = (TextView) findViewById(R.id.nav_bar_tv_title);
        tvTitle.setText(title);
        btnLeft = (Button) findViewById(R.id.nav_bar_btn_left);
        btnLeft.setText(left);
        btnRight = (Button) findViewById(R.id.nav_bar_btn_right);
        btnRight.setText(right);
        if(title == null) tvTitle.setVisibility(View.INVISIBLE);
        if(left == null) btnLeft.setVisibility(View.INVISIBLE);
        if(right == null) btnRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置标题
     */
    protected void setNavBarTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 获取标题
     */
    protected String getNavBarTitle() {
        return tvTitle.getText().toString();  
    }

    /**
     * 设置左按钮文本
     */
    protected void setNavBarLeftButtonText(String text) {
        btnLeft.setText(text);
    }

    /**
     * 设置右按钮文本
     */
    protected void setNavBarRightButtonText(String text) {
        btnRight.setText(text);
    }

    /**
     * 获取左按钮文本
     */
    protected String getNavBarLeftButtonText() {
        return btnLeft.getText().toString();
    }

    /**
     * 获取右按钮文本
     */
    protected String getNavBarRightButtonText() {
        return btnRight.getText().toString();
    }

    /**
     * 左按钮监听函数
     */
    public void onNavBarLeftButtonClick(View view) {
        finish();
    }

    /**
     * 右按钮监听函数
     */
    public void onNavBarRightButtonClick(View view) {

    }

    /**
     * 标题监听
     */
    public void onNavBarTitleClick(View view) {
    }

    /**
     * 设置左按钮可视
     */
    protected void setNavBarLeftButtonVisible(boolean visible) {
        if(visible) btnLeft.setVisibility(View.VISIBLE);
        else btnLeft.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置右按钮可视
     */
    protected void setNavBarRightButtonVisible(boolean visible) {
        if(visible) btnRight.setVisibility(View.VISIBLE);
        else btnRight.setVisibility(View.INVISIBLE);
    }

}
