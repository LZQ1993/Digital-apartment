package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class DangerSResultInfo {
	public String Result;
	public String Tip;
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getTip() {
		return Tip;
	}
	public void setTip(String tip) {
		Tip = tip;
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public static DangerSResultInfo fromJson(String json) {
		return JsonUtil.fromJson(json, DangerSResultInfo.class);
	}
}
