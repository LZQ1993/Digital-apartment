package com.chinawit.scloud.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.chinawit.scloud.bean.User;

/**
 * 用户信息
 * 
 */
public class UserInfo {

    private static User user = null;
    
    /**
     * 获取用户
     */
    public static User getUser() {
        if (user == null) {
            SharedPreferences sp = App.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
            //获取用户
            user = User.fromCrypto(sp.getString("cryptoUser", ""));
        }
        return user;
    }

    /**
     * 登录
     */
    public static void signIn(User user) {
        UserInfo.user = user;
        Editor ed = App.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE).edit();
        ed.putString("cryptoUser", user.toCrypto());
        ed.commit();
    }

    /**
     * 注销
     */
    public static void signOut() {
        UserInfo.user = null;
        Editor ed = App.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE).edit();
        ed.putString("cryptoUser", "");
        ed.commit();
    }

}
