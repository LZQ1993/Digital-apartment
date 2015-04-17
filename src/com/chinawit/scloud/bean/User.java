package com.chinawit.scloud.bean;

import com.chinawit.scloud.crypto.Des3Util;
import com.json.JsonUtil;
/**
 * 用户实体类
 */
public class User {

	public int Id;
    public String UserName;
    public String Password;
    public String Power;
    public String Type;
    public String RealName;
    public String IsStop;
    public String College;
    public String Professional ;
    public String ClassAbb;
    public String BindTime ;
    public int Verified;
    public String Tel;
    
   
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPower() {
		return Power;
	}

	public void setPower(String power) {
		Power = power;
	}



	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getIsStop() {
		return IsStop;
	}

	public void setIsStop(String isStop) {
		IsStop = isStop;
	}

	public String getCollege() {
		return College;
	}

	public void setCollege(String college) {
		College = college;
	}

	public String getProfessional() {
		return Professional;
	}

	public void setProfessional(String professional) {
		Professional = professional;
	}

	public String getClassAbb() {
		return ClassAbb;
	}

	public void setClassAbb(String classAbb) {
		ClassAbb = classAbb;
	}

	public String getBindTime() {
		return BindTime;
	}

	public void setBindTime(String bindTime) {
		BindTime = bindTime;
	}
	
	

	public int getVerified() {
		return Verified;
	}

	public void setVerified(int verified) {
		Verified = verified;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static User fromJson(String json) {
		return JsonUtil.fromJson(json, User.class);
	}

	/**
	 * Des3序列化
	 */
	public String toCrypto() {
		try {
			return Des3Util.encode(toJson());
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Des3反序列化
	 */
	public static User fromCrypto(String text) {
		try {
			return fromJson(Des3Util.decode(text));
		} catch (Exception e) {
			return null;
		}
	}
}
