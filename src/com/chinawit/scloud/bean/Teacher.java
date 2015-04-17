package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class Teacher {

	
	public int TeacherID;
    public String TeacherName;
    public String Tel;
    public String Sex;
    public String Unit;
    public String Post;
    public String[] Classes;
    public String Certification;
    public String IsInstructure;
 
    
	
	public int getTeacherID() {
		return TeacherID;
	}

	public void setTeacherID(int teacherID) {
		TeacherID = teacherID;
	}

	public String getTeacherName() {
		return TeacherName;
	}

	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getPost() {
		return Post;
	}

	public void setPost(String post) {
		Post = post;
	}



	public String[] getClasses() {
		return Classes;
	}

	public void setClasses(String[] classes) {
		Classes = classes;
	}

	public String getCertification() {
		return Certification;
	}

	public void setCertification(String certification) {
		Certification = certification;
	}

	public String getIsInstructure() {
		return IsInstructure;
	}

	public void setIsInstructure(String isInstructure) {
		IsInstructure = isInstructure;
	}

	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static Teacher fromJson(String json) {
		return JsonUtil.fromJson(json, Teacher.class);
	}
}
