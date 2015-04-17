package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class Term {
	public String Term;
	public String IsCurrennt;
	
	
	public String getTerm() {
		return Term;
	}
	public void setTerm(String term) {
		Term = term;
	}
	public String getIsCurrennt() {
		return IsCurrennt;
	}
	public void setIsCurrennt(String isCurrennt) {
		IsCurrennt = isCurrennt;
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static Term fromJson(String json) {
		return JsonUtil.fromJson(json, Term.class);
	}
}
