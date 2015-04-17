package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class DormScore {
      public String DormNum;
      public String Year;
      public String Week;
      public String College;
      public String Score;
      public String ScoreLevel;
      public String Ground;
      public String Smell;
      public String Window;
      public String Garbage;
      public String Toilet;
      public String Goods ;
      public String Bed;
      public String Smoker;
      public String Whole;
      public String Attitude;
      public String IIIegal; 
      public String IIIegalContext; 
      public String OtherContext; 
      public String ReMark ;
      public String CheckType;
      public String CheckClass;
      public String CheckTime;



	public String getDormNum() {
		return DormNum;
	}

	public void setDormNum(String dormNum) {
		DormNum = dormNum;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getWeek() {
		return Week;
	}

	public void setWeek(String week) {
		Week = week;
	}

	public String getCollege() {
		return College;
	}

	public void setCollege(String college) {
		College = college;
	}

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		Score = score;
	}

	public String getScoreLevel() {
		return ScoreLevel;
	}

	public void setScoreLevel(String scoreLevel) {
		ScoreLevel = scoreLevel;
	}

	public String getGround() {
		return Ground;
	}

	public void setGround(String ground) {
		Ground = ground;
	}

	public String getSmell() {
		return Smell;
	}

	public void setSmell(String smell) {
		Smell = smell;
	}

	public String getWindow() {
		return Window;
	}

	public void setWindow(String window) {
		Window = window;
	}

	public String getGarbage() {
		return Garbage;
	}

	public void setGarbage(String garbage) {
		Garbage = garbage;
	}

	public String getToilet() {
		return Toilet;
	}

	public void setToilet(String toilet) {
		Toilet = toilet;
	}

	public String getGoods() {
		return Goods;
	}

	public void setGoods(String goods) {
		Goods = goods;
	}

	public String getBed() {
		return Bed;
	}

	public void setBed(String bed) {
		Bed = bed;
	}

	public String getSmoker() {
		return Smoker;
	}

	public void setSmoker(String smoker) {
		Smoker = smoker;
	}

	public String getWhole() {
		return Whole;
	}

	public void setWhole(String whole) {
		Whole = whole;
	}

	public String getAttitude() {
		return Attitude;
	}

	public void setAttitude(String attitude) {
		Attitude = attitude;
	}

	public String getIIIegal() {
		return IIIegal;
	}

	public void setIIIegal(String iIIegal) {
		IIIegal = iIIegal;
	}

	public String getIIIegalContext() {
		return IIIegalContext;
	}

	public void setIIIegalContext(String iIIegalContext) {
		IIIegalContext = iIIegalContext;
	}

	public String getReMark() {
		return ReMark;
	}

	public void setReMark(String reMark) {
		ReMark = reMark;
	}

	public String getCheckType() {
		return CheckType;
	}

	public void setCheckType(String checkType) {
		CheckType = checkType;
	}

	public String getOtherContext() {
		return OtherContext;
	}

	public void setOtherContext(String otherContext) {
		OtherContext = otherContext;
	}

	public String getCheckClass() {
		return CheckClass;
	}

	public void setCheckClass(String checkClass) {
		CheckClass = checkClass;
	}

	public String getCheckTime() {
		return CheckTime;
	}

	public void setCheckTime(String checkTime) {
		CheckTime = checkTime;
	}

	public String toJson() {
	    	return JsonUtil.toJson(this);
	    }
	    
	    public static DormScore fromJson(String json) {
	    	return JsonUtil.fromJson(json, DormScore.class);
	    } 
}
