package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class SubmitDanger {
	public String TroubleType;//隐患类型；
	public String TroubleSpot;//隐患地点；
	public String Description;//隐患描述；
	public String SubmitPeopleID;//用户id
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
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getSubmitPeopleID() {
		return SubmitPeopleID;
	}
	public void setSubmitPeopleID(String submitPeopleID) {
		SubmitPeopleID = submitPeopleID;
	}

	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static SubmitDanger fromJson(String json) {
		return JsonUtil.fromJson(json, SubmitDanger.class);
	}
}
