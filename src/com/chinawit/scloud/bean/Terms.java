package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class Terms {
	public String ResultCode;
    public List<Term> Result;
    
    
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public List<Term> getResult() {
		return Result;
	}
	public void setResult(List<Term> result) {
		Result = result;
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public int getTermsCount(){
		return this.Result.size();
	}
	
	
	public static Terms fromJson(String json) {
		return JsonUtil.fromJson(json, Terms.class);
	}
    
}
