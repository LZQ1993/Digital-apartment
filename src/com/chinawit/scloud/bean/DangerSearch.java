package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class DangerSearch {
	public String TroubleType;
	public String TroubleSpot;
	public String keyword;
	public String state;
	public String getTroubleType() {
		return TroubleType;
	}
	public void setTroubleType(String troubleType) {
		TroubleType = troubleType;
	}
	public String getTroubleSpot() {
		return TroubleSpot;
	}
	public void setTroubleSpot(String troubleSpot) {
		TroubleSpot = troubleSpot;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static DangerSearch fromJson(String json) {
		return JsonUtil.fromJson(json, DangerSearch.class);
	}
}
