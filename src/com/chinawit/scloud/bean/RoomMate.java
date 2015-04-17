package com.chinawit.scloud.bean;

import com.json.JsonUtil;


public class RoomMate {

	 public String StudentName ;
	 public String StudentBedNum ;
	 public String StudentNum ;
	 public String Post ;
	 public String Face ;
	 public String Abbreviation;
	 public String TeacherName;
	 
	 
	 
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}

	public String getStudentBedNum() {
		return StudentBedNum;
	}
	public void setStudentBedNum(String studentBedNum) {
		StudentBedNum = studentBedNum;
	}
	public String getStudentNum() {
		return StudentNum;
	}
	public void setStudentNum(String studentNum) {
		StudentNum = studentNum;
	}
	public String getPost() {
		return Post;
	}
	public void setPost(String post) {
		Post = post;
	}
	public String getFace() {
		return Face;
	}
	public void setFace(String face) {
		Face = face;
	}
	public String getAbbreviation() {
		return Abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		Abbreviation = abbreviation;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	} 
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static RoomMate fromJson(String json) {
		return JsonUtil.fromJson(json, RoomMate.class);
	}

	
	 
}
