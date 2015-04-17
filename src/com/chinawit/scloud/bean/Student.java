package com.chinawit.scloud.bean;

import com.json.JsonUtil;

/**
 * 成员信息类
 *
 
 */
public class Student {
	public int Id ;
    public String StudentName ;
    public String StudentDormNum;
    public String StudentBedNum ;
    public String TeacherName ;
    public String TeacherTel ;
    public String College ;
    public String Professional ;
    public String AcademicYear;
    public String StudentClass ;
    public String IdCardNum ;
    public String PhotoPath ;
    public String Face ;
    public String TelNum ;
    public String Post ;
    public String StudentNum ;
    public String Sex ;
    public String Nation ;
    public String MobileTel;
    public String UniComeTel ;
    public String TeleComTel ;
    public String FatherName;
    public String FatherTel; 
    public String MotherName; 
    public String MotherTel; 
    public String Address;
    public String DormType;
    public String Abbreviation;
    
    
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getStudentName() {
		return StudentName;
	}
	public void setStudentName(String studentName) {
		StudentName = studentName;
	}
	public String getStudentDormNum() {
		return StudentDormNum;
	}
	public void setStudentDormNum(String studentDormNum) {
		StudentDormNum = studentDormNum;
	}
	public String getStudentBedNum() {
		return StudentBedNum;
	}
	public void setStudentBedNum(String studentBedNum) {
		StudentBedNum = studentBedNum;
	}
	public String getTeacherName() {
		return TeacherName;
	}
	public void setTeacherName(String teacherName) {
		TeacherName = teacherName;
	}
	public String getTeacherTel() {
		return TeacherTel;
	}
	public void setTeacherTel(String teacherTel) {
		TeacherTel = teacherTel;
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
	public String getAcademicYear() {
		return AcademicYear;
	}
	public void setAcademicYear(String academicYear) {
		AcademicYear = academicYear;
	}
	public String getStudentClass() {
		return StudentClass ;
	}
	public void setStudentClass(String StudentClass) {
		this.StudentClass  = StudentClass;
	}
	public String getIdCardNum() {
		return IdCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		IdCardNum = idCardNum;
	}
	public String getPhotoPath() {
		return PhotoPath;
	}
	public void setPhotoPath(String photoPath) {
		PhotoPath = photoPath;
	}
	public String getFace() {
		return Face;
	}
	public void setFace(String face) {
		Face = face;
	}
	public String getTelNum() {
		return TelNum;
	}
	public void setTelNum(String telNum) {
		TelNum = telNum;
	}
	public String getPost() {
		return Post;
	}
	public void setPost(String post) {
		Post = post;
	}
	public String getStudentNum() {
		return StudentNum;
	}
	public void setStudentNum(String studentNum) {
		StudentNum = studentNum;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getNation() {
		return Nation;
	}
	public void setNation(String nation) {
		Nation = nation;
	}
	public String getMobileTel() {
		return MobileTel;
	}
	public void setMobileTel(String mobileTel) {
		MobileTel = mobileTel;
	}
	public String getUniComeTel() {
		return UniComeTel;
	}
	public void setUniComeTel(String uniComeTel) {
		UniComeTel = uniComeTel;
	}
	public String getTeleComTel() {
		return TeleComTel;
	}
	public void setTeleComTel(String teleComTel) {
		TeleComTel = teleComTel;
	}
	public String getFatherName() {
		return FatherName;
	}
	public void setFatherName(String fatherName) {
		FatherName = fatherName;
	}
	public String getFatherTel() {
		return FatherTel;
	}
	public void setFatherTel(String fatherTel) {
		FatherTel = fatherTel;
	}
	public String getMotherName() {
		return MotherName;
	}
	public void setMotherName(String motherName) {
		MotherName = motherName;
	}
	public String getMotherTel() {
		return MotherTel;
	}
	public void setMotherTel(String motherTel) {
		MotherTel = motherTel;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getDormType() {
		return DormType;
	}
	public void setDormType(String dormType) {
		DormType = dormType;
	}
	
	public String getAbbreviation() {
		return Abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		Abbreviation = abbreviation;
	}
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	public static Student fromJson(String json) {
		return JsonUtil.fromJson(json, Student.class);
	}
	
}

