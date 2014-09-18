
package com.will.loans.beans;

import com.will.loans.beans.bean.BaseBeans;
import com.will.loans.beans.bean.UserInfo;

public class UserinfoCache extends BaseBeans {

	private static final long serialVersionUID = 1L;

	public static String token;
	public static String userId;

	public static UserInfo userInfo;

	public static String copyStr;

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		UserinfoCache.token = token;
	}

	public static UserInfo getUserInfo() {
		return userInfo;
	}

	public static void setUserInfo(UserInfo userInfo) {
		UserinfoCache.userInfo = userInfo;
	}

	public static String getCopyStr() {
		return copyStr;
	}

	public static void setCopyStr(String copyStr) {
		UserinfoCache.copyStr = copyStr;
	}
}
