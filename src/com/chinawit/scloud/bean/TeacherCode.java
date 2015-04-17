package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;

public class TeacherCode {
	
	public String ResultCode;
    public List<Teacher> Teacher;
    
    
    
	public String getResultCode() {
		return ResultCode;
	}
	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}
	public List<Teacher> getTeacher() {
		return Teacher;
	}
	public void setTeacher(List<Teacher> teacher) {
		Teacher = teacher;
	}
    
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	public int getTeacherCount(){
		return this.Teacher.size();
	}
	
	
	public static TeacherCode fromJson(String json) {
		return JsonUtil.fromJson(json, TeacherCode.class);
	}

}
