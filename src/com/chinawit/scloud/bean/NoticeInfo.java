package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class NoticeInfo {
	public int ID;
	public String NoticeType;
	public String NoticeContent;
	public String RemindTime;//提醒时间
	public String SubmitTime;//截至时间
	public String State;//状态
	public String watchMan;
	public String college;
	
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNoticeType() {
		return NoticeType;
	}

	public void setNoticeType(String noticeType) {
		NoticeType = noticeType;
	}

	public String getNoticeContent() {
		return NoticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		NoticeContent = noticeContent;
	}

	public String getRemindTime() {
		return RemindTime;
	}

	public void setRemindTime(String remindTime) {
		RemindTime = remindTime;
	}

	public String getSubmitTime() {
		return SubmitTime;
	}

	public void setSubmitTime(String submitTime) {
		SubmitTime = submitTime;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getWatchMan() {
		return watchMan;
	}

	public void setWatchMan(String watchMan) {
		this.watchMan = watchMan;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static NoticeInfo fromJson(String json) {
		return JsonUtil.fromJson(json, NoticeInfo.class);
	}

}
