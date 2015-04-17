package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class PrimaryResult {
	
	
	public int CheckId;
	public String Week;
	public String Score;
	
	
	public int getCheckId() {
		return CheckId;
	}
	public void setCheckId(int checkId) {
		CheckId = checkId;
	}

	public String getWeek() {
		return Week;
	}
	public void setWeek(String week) {
		Week = week;
	}
	public String getScore() {
		return Score;
	}
	public void setScore(String score) {
		Score = score;
	}
	
	public String toJson() {
    	return JsonUtil.toJson(this);
    }
    
    public static PrimaryResult fromJson(String json) {
    	return JsonUtil.fromJson(json, PrimaryResult.class);
    } 

}
