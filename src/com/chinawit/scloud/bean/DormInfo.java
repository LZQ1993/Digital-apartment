package com.chinawit.scloud.bean;

import java.util.List;

import com.json.JsonUtil;
/**
 * 房间类
 *
 */
public class DormInfo {

	public String DormNum;
	public String College;
	public String CaptainBed;
	public List<RoomMate> RoomMate;

	public String getDormNum() {
		return DormNum;
	}

	public void setDormNum(String DormNum) {
		this.DormNum = DormNum;
	}
	public String getCollege() {
		return College;
	}

	public void setCollege(String college) {
		College = college;
	}

	public String getCaptainBed() {
		return CaptainBed;
	}

	public void setCaptainBed(String captainBed) {
		CaptainBed = captainBed;
	}

	public List<RoomMate> getRoomMate() {
		return RoomMate;
	}

	public void setroomMate(List<RoomMate> RoomMate) {
		this.RoomMate = RoomMate;
	}

	/**
	 * 获取寝室长姓名
	 */
	public String getCaptainName() {
		if (Integer.parseInt(CaptainBed)== 0) {
			return "未指定";
		}
		//循环查找
		for (int n=0;n<this.RoomMate.size();n++) {
			RoomMate mate = this.RoomMate.get(n);
			if(Integer.parseInt(mate.getStudentBedNum()) == Integer.parseInt(CaptainBed)){
				return mate.getStudentName();
			}
		}
		//未找到
		return "未指定";
	}
	
	/**
	 * 获取舍员数量
	 */
	public int getRoomMateCount(){
		return this.RoomMate.size();
	}
	/**
	 * 初始化
	 */
	private void init(){
		//填充职务
		for(int n=0; n < RoomMate.size(); n++){
			RoomMate mate = RoomMate.get(n);
			//填充职务
			if(mate.getPost() == null || mate.getPost().equals("")) {
				mate.setPost("无");
			}
		}
	}
	

	

	public String toJson() {
		return JsonUtil.toJson(this);
	}

	public static DormInfo fromJson(String json) {
		DormInfo dormInfo =  JsonUtil.fromJson(json, DormInfo.class);
		
		boolean b = true;
		while(b){
			b=false;
			for(int n=0; n<dormInfo.getRoomMateCount(); n++) {
				RoomMate mate = dormInfo.getRoomMate().get(n);
				if(mate == null) {
					b=true;
					dormInfo.getRoomMate().remove(n);
					break;
				}
			}
		}
		dormInfo.init();
		return dormInfo;
	}

}
