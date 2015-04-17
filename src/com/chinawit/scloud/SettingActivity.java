package com.chinawit.scloud;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chinawit.scloud.app.SettingInfo;


public class SettingActivity extends NavBarActivity {

    /**
     * onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initNavBar("设置","<返回",null);
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {       
         /*
         * 登陆设置
         */
        //记住密码
        CheckBox cbSavePwd  = (CheckBox) findViewById(R.id.setting_cb_autoLogin);
        cbSavePwd.setChecked(SettingInfo.isSavePassword());
        cbSavePwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                SettingInfo.setSavePassword(isChecked);
            }
        });
        /*
         * 二维码设置
         */
        //蜂鸣
        CheckBox cbQrcodePlayBeepSound = (CheckBox) findViewById(R.id.setting_cb_qr_playBeepSound);
        cbQrcodePlayBeepSound.setChecked(SettingInfo.isQrcodePlayBeepSound());
        cbQrcodePlayBeepSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                SettingInfo.setQrcodePlayBeepSound(isChecked);
            }
        });
        //震动
        CheckBox cbQrcodeVibrate = (CheckBox) findViewById(R.id.setting_cb_qr_playVibrate);
        cbQrcodeVibrate.setChecked(SettingInfo.isQrcodeVibrate());
        cbQrcodeVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                SettingInfo.setQrcodeVibrate(isChecked);
            }
        });    
        /*
         * 更新设置
         */
        //自动更新
        CheckBox cbAutoUpdate = (CheckBox) findViewById(R.id.setting_cb_autoUpdate);
        cbAutoUpdate.setChecked(SettingInfo.isAutoUpdate());
        cbAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                SettingInfo.setAutoUpdate(isChecked);
            }
        });
    }

    /**
     * 更新检查
     */
    public void checkUpdate(View view) {
        //弹出提示菜单
        new AlertDialog.Builder(this) 
        .setTitle("提示")
        .setMessage("更新检查暂时不可用！")
        .setNegativeButton("确定", null)
        .show();
    }

    
  
    
}
