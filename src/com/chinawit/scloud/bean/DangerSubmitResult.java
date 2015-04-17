package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class DangerSubmitResult {
	public String ResultCode;
    public List<DangerSResultInfo> Result;
    
    
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public List<DangerSResultInfo> getResult() {
		return Result;
	}
	public void setResult(List<DangerSResultInfo> result) {
		Result = result;
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public static DangerSubmitResult fromJson(String json) {
		return JsonUtil.fromJson(json, DangerSubmitResult.class);
	}
    
    
}
