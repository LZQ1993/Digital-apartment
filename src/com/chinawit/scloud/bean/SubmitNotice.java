package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class SubmitNotice {
	
	public String NoticeType;//通知类型；
	public String NoticeContent;//通知内容描述；
	public String SubmitPeopleID;//提交人（通知人）编号 
	public String NoticeValidTime;//通知有效时间,精确到分钟
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
	public String getSubmitPeopleID() {
		return SubmitPeopleID;
	}
	public void setSubmitPeopleID(String submitPeopleID) {
		SubmitPeopleID = submitPeopleID;
	}
	public String getNoticeValidTime() {
		return NoticeValidTime;
	}
	public void setNoticeValidTime(String noticeValidTime) {
		NoticeValidTime = noticeValidTime;
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static SubmitNotice fromJson(String json) {
		return JsonUtil.fromJson(json, SubmitNotice.class);
	}

}
