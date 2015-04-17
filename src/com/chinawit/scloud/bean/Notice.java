package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;



public class Notice {
	public String ResultCode;
    public List<NoticeInfo> NoticeInfo;
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public List<NoticeInfo> getNoticeInfo() {
		return NoticeInfo;
	}
	public void setNoticeInfo(List<NoticeInfo> noticeInfo) {
		NoticeInfo = noticeInfo;
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public int getNoticeInfoCount(){
		return this.NoticeInfo.size();
	}

	public static Notice fromJson(String json) {
		return JsonUtil.fromJson(json, Notice.class);
	}

}