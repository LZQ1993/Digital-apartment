package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class Dorm_lst {
	
	
	public String College;
    public String DormNum;
    
    
    
	public String getCollege() {
		return College;
	}
	public void setCollege(String college) {
		College = college;
	}
	public String getDormNum() {
		return DormNum;
	}
	public void setDormNum(String dormNum) {
		DormNum = dormNum;
	}
    
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static Dorm_lst fromJson(String json) {
		return JsonUtil.fromJson(json, Dorm_lst.class);
	}
}