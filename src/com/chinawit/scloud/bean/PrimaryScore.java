package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class PrimaryScore {
	public String ResultCode;
	public List<PrimaryResult> PrimaryResult;
	
	
	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	public List<PrimaryResult> getPrimaryResult() {
		return PrimaryResult;
	}

	public void setPrimaryScore(List<PrimaryResult> PrimaryResult) {
		this.PrimaryResult = PrimaryResult;
	}
	/**
	 * 获取数量
	 */
	public int getPrimaryResultCount(){
		return this.PrimaryResult.size();
	}
	public String toJson() {
    	return JsonUtil.toJson(this);
    }
    
    public static PrimaryScore fromJson(String json) {
    	return JsonUtil.fromJson(json, PrimaryScore.class);
    } 

}
