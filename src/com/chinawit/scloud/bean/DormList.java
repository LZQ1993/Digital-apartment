package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class DormList {
	
	public List<Dorm_lst> Dorm_lst;

	
	public List<Dorm_lst> getDorm_lst() {
		return Dorm_lst;
	}

	public void setDorm_lst(List<Dorm_lst> dorm_lst) {
		Dorm_lst = dorm_lst;
	}

	
	/**
	 * 获取舍员数量
	 */
	public int getDormlistCount(){
		return this.Dorm_lst.size();
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static DormList fromJson(String json) {
		return JsonUtil.fromJson(json, DormList.class);
	}

	
}
