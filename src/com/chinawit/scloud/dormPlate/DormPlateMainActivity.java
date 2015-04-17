package com.chinawit.scloud.dormPlate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chinawit.scloud.NavBarActivity;
import com.chinawit.scloud.R;
import com.chinawit.scloud.app.NetworkInfo;
import com.chinawit.scloud.bean.DormInfo;
import com.chinawit.scloud.dormSearch.DormInfoActivity;
import com.http.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class DormPlateMainActivity extends NavBarActivity {

    /** 
     * onCreate 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dorm_plate_main);
        initNavBar("门牌扫描", "<返回", null);
    }

    /**
     * 打开扫描器
     * @param view
     */
    public void openQrcodeCapture(View view) {
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_DEFAULT);
    }

    /**
     * 获取返回信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DEFAULT) {
            if (resultCode == RESULT_OK) {
                showDefalt();
                getDormPlateByDormNumTask(data.getStringExtra("data"));
            }
            else if (resultCode == RESULT_ERROR) {
                showError();
            } else {
                showDefalt();
            }
        }
    }

    /**
     * 显示默认页面
     */
    private void showDefalt() {
        ImageView iv = (ImageView) findViewById(R.id.dorm_plate_main_iv);
        iv.setImageResource(R.drawable.qr_capture_intro);
    }

    /**
     * 显示错误页面
     */
    private void showError() {
        ImageView iv = (ImageView) findViewById(R.id.dorm_plate_main_iv);
        iv.setImageResource(R.drawable.qr_capture_warning);
    }

    /**
     * 网络任务-通过寝室号获取门牌
     */
    public void getDormPlateByDormNumTask(String dormNum) {
        showProgressDialog();    
        String url = NetworkInfo.getServiceUrl() + "GetDormPlateByDormNum.ashx";
        RequestParams params = new RequestParams();
        params.put("DormNum", dormNum);
        HttpUtil.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onFailure(Throwable error) {
                dismissProgressDialog();
                new AlertDialog.Builder(DormPlateMainActivity.this)    
                .setTitle("错误")  
                .setMessage("网络访问失败，请检查网络！")  
                .setPositiveButton("确定",null)
                .show();
            }

            @Override
            public void onSuccess(String content) {
                dismissProgressDialog();
                DormInfo dorm = null;
                try {
                    dorm = DormInfo.fromJson(content);
                } catch (Exception e) {
                    new AlertDialog.Builder(DormPlateMainActivity.this)    
                    .setTitle("错误")  
                    .setMessage("数据解析异常")  
                    .setPositiveButton("确定", null)
                    .show();
                    return;
                }
                //数据为空
                if (dorm == null||dorm.getRoomMateCount()==0) {
                    new AlertDialog.Builder(DormPlateMainActivity.this)    
                    .setTitle("提示")  
                    .setMessage("未获取到寝室信息\n或该寝室不存在！")  
                    .setPositiveButton("确定",null)
                    .show();
                    return;
                }
                //跳转到相关页面
                Intent intent = new Intent();
                intent.putExtra("jsonDormPlate",dorm.toJson());
                intent.setClass(DormPlateMainActivity.this, DormInfoActivity.class);
                startActivity(intent);
            }
        });
    }

   /* *//**
     * 导航右键
     *//*
    @Override
    public void onNavBarLeftButtonClick(View view) {
    	 onExit();
    }
    
    *//**
     * 返回键
     *//*
    @Override
    public void onBackPressed() {
        onExit();
    }
    
    *//**
     * 退出
     *//*
    public void onExit() {
        new AlertDialog.Builder(this)
        .setTitle("提示：退出寝室考核")
        .setMessage("您确定要退出门牌扫描模块吗？")
        .setPositiveButton("确定",new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("取消", null)
        .show();
    }*/
}
