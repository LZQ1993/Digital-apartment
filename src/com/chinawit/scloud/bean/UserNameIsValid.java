package com.chinawit.scloud.bean;

import com.json.JsonUtil;

public class UserNameIsValid {
          public String UserName;

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
		}
          
		public String toJson() {
			return JsonUtil.toJson(this);
		}
		
		public static UserNameIsValid fromJson(String json) {
			return JsonUtil.fromJson(json, UserNameIsValid.class);
		}
}
