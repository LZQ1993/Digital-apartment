package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class DormCheckRecord {
                
	public String DormNum;                 
	public String Ground;
	public String Smell;
	public String Window;
	public String Garbage;
	public String Toilet;
	public String Goods;
	public String Bed;
	public String Whole;
	public String Attitude;
	public String Smoker;
	public String IIIegal;
	public String IIIegalContent;
	public String IIIegalOtherContent;
	public String ReMark;
	public String CheckType;     
    public String SubmitMemberID;
    public String DataType;
    
	

	public String getDormNum() {
		return DormNum;
	}

	public void setDormNum(String dormNum) {
		DormNum = dormNum;
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

	public String getSmoker() {
		return Smoker;
	}

	public void setSmoker(String smoker) {
		Smoker = smoker;
	}

	public String getIIIegal() {
		return IIIegal;
	}

	public void setIIIegal(String iIIegal) {
		IIIegal = iIIegal;
	}

	public String getIIIegalContent() {
		return IIIegalContent;
	}

	public void setIIIegalContent(String iIIegalContent) {
		IIIegalContent = iIIegalContent;
	}

	public String getIIIegalOtherContent() {
		return IIIegalOtherContent;
	}

	public void setIIIegalOtherContent(String iIIegalOtherContent) {
		IIIegalOtherContent = iIIegalOtherContent;
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

	public String getSubmitMemberID() {
		return SubmitMemberID;
	}

	public void setSubmiMemberID(String submitMemberID) {
		SubmitMemberID = submitMemberID;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}
    /**
	 * 计算得分
	 */
    public String calculatePoint() {
		int point = 0 +
				Integer.parseInt(this.Ground) +
				Integer.parseInt(this.Smell) +
				Integer.parseInt(this.Window )+
				Integer.parseInt(this.Goods )+
				Integer.parseInt(this.Bed )+
				Integer.parseInt(this.Toilet )+
				Integer.parseInt(this.Garbage )+
				Integer.parseInt(this.Whole )+
				Integer.parseInt(this.Smoker )+
				Integer.parseInt(this.Attitude);	
		
		return String.valueOf(point);
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static DormCheckRecord fromJson(String json) {
		return JsonUtil.fromJson(json, DormCheckRecord.class);
	}

	@Override
	public String toString() {
		return toJson();
	}

}

