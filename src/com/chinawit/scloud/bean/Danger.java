package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class Danger {
	public String ResultCode;
    public List<DangerInfo> Result;
    
    
    
	public String getResultCode() {
		return ResultCode;
	}


	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}


	public List<DangerInfo> getResult() {
		return Result;
	}


	public void setResult(List<DangerInfo> result) {
		Result = result;
	}


	public int getDangerInfoCount(){
		return this.Result.size();
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public static Danger fromJson(String json) {
		return JsonUtil.fromJson(json, Danger.class);
	}

}
