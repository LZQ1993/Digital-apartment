package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class CWeek {

	public String currentWeek;

	public String getCurrentWeek() {
		return currentWeek;
	}

	public void setCurrentWeek(String currentWeek) {
		this.currentWeek = currentWeek;
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}

	public static CWeek fromJson(String json) {
		return JsonUtil.fromJson(json, CWeek.class);
	}
}
