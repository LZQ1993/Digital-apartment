package com.chinawit.scloud.app;

import android.app.Application;
import android.util.Log;


public class App extends Application {

    private final static String TAG = "SCloud-App";
    private static Application context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.i(TAG, "[" + AppInfo.getApplicationName() + "]");
        Log.i(TAG, "PackageName : " + AppInfo.getPackageName());
        Log.i(TAG, "VersionName : " + AppInfo.getVersionName());
        Log.i(TAG, "VersionCode : " + AppInfo.getVersionCode());
        Log.i(TAG, "UpdateTime : " + AppInfo.getUpdateTime());
        Log.i(TAG, AppInfo.getAuthorInfo());
    }

    public static Application getContext() {
        return context;
    }
}
