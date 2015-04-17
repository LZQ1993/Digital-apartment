package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class DangerInfo {
	public int ID;
	public String TroubleType;
	public String TroubleSpot;//地点
	public String Description;//描述
	public String WatchMan;//发现人
	public String College;
	public String state;
	public String SubmitTime;
	public String ProceedTime;//整改时间
	public String ProceedMethod;

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

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

	public String getWatchMan() {
		return WatchMan;
	}

	public void setWatchMan(String watchMan) {
		WatchMan = watchMan;
	}

	public String getCollege() {
		return College;
	}

	public void setCollege(String college) {
		College = college;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubmitTime() {
		return SubmitTime;
	}

	public void setSubmitTime(String submitTime) {
		SubmitTime = submitTime;
	}

	public String getProceedTime() {
		return ProceedTime;
	}

	public void setProceedTime(String proceedTime) {
		ProceedTime = proceedTime;
	}

	public String getProceedMethod() {
		return ProceedMethod;
	}

	public void setProceedMethod(String proceedMethod) {
		ProceedMethod = proceedMethod;
	}

	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static DangerInfo fromJson(String json) {
		return JsonUtil.fromJson(json, DangerInfo.class);
	}

	
}
