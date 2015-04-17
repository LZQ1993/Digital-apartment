package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class SeniorResult {
	public int CheckId;
	public String DormNum;
	public String Score;
	
	
	public int getCheckId() {
		return CheckId;
	}

	public void setCheckId(int checkId) {
		CheckId = checkId;
	}

	public String getDormNum() {
		return DormNum;
	}

	public void setDormNum(String dormNum) {
		DormNum = dormNum;
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
    
    public static SeniorResult fromJson(String json) {
    	return JsonUtil.fromJson(json, SeniorResult.class);
    } 

}
