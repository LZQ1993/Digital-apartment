package com.chinawit.scloud.app;

/**
 * 网络服务信息
 * 
 */
public class NetworkInfo {

    private final static String serviceUrl = "http://www.xqtian.com/Client/";
    private final static String homePageUrl = "http://pupboss.com/scloud/";
    
    /**
     * 获取服务器地址
     */
    public static String getServiceUrl() {
        return serviceUrl;
    }
    
    /**
     * 获取主页地址
     */
    public static String getHomePageUrl() {
    	return homePageUrl;
    }

}
