package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class SeniorScore {
	public String ResultCode;
	public List<SeniorResult> SeniorResult;
	
	
	

	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	public List<SeniorResult> getSeniorResult() {
		return SeniorResult;
	}

	public void setSeniorSc(List<SeniorResult> SeniorResult) {
		this.SeniorResult = SeniorResult;
	}
	
	/**
	 * 获取数量
	 */
	public int getSeniorResultCount(){
		return this.SeniorResult.size();
	}

	public String toJson() {
    	return JsonUtil.toJson(this);
    }
    
    public static SeniorScore fromJson(String json) {
    	return JsonUtil.fromJson(json, SeniorScore.class);
    } 
}
