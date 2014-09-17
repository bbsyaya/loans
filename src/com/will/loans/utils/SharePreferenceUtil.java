package com.will.loans.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA. User: peng Date: 13-12-20 Time: 上午8:47 To change
 * this template use File | Settings | File Templates.
 */

public class SharePreferenceUtil {
	private SharedPreferences sp;

	private SharedPreferences.Editor editor;

	private static SharePreferenceUtil sUserPref;

	private final static String USER_INFO = "userInfo";

	@SuppressLint("CommitPrefEdits")
	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static synchronized SharePreferenceUtil getUserPref(Context context) {
		if (sUserPref == null) {
			sUserPref = new SharePreferenceUtil(context, USER_INFO);
		}
		return sUserPref;
	}

	public void setUsername(String username) {
		editor.putString("username", username);
		editor.commit();
	}

	public String getUsername() {
		return sp.getString("username", "");
	}

	// 用户的密码
	public void setPasswd(String passwd) {
		editor.putString("passwd", passwd);
		editor.commit();
	}

	public String getPasswd() {
		return sp.getString("passwd", "");
	}

	public void setToken(String token) {
		editor.putString("token", token);
		editor.commit();
	}

	public String getToken() {
		return sp.getString("token", null);
	}

	// 用户的昵称
	public String getNickName() {
		return sp.getString("nickname", "");
	}

	public void setNickName(String name) {
		editor.putString("nickname", name);
		editor.commit();
	}

	// 用户的邮箱
	public String getEmail() {
		return sp.getString("email", "");
	}

	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}

	// 用户自己的头像
	public String getImg() {
		return sp.getString("img", "");
	}

	public void setImg(String i) {
		editor.putString("img", i);
		editor.commit();
	}

	// 是否在后台运行标记
	public void setIsStart(boolean isStart) {
		editor.putBoolean("isStart", isStart);
		editor.commit();
	}

	public boolean getIsStart() {
		return sp.getBoolean("isStart", false);
	}

	// 是否第一次运行本应用
	public void setIsFirst(boolean isFirst) {
		editor.putBoolean("isFirst", isFirst);
		editor.commit();
	}

	public boolean getisFirst() {
		return sp.getBoolean("isFirst", true);
	}

	public void setLoadingImg(String loadingImg) {
		editor.putString("loading", loadingImg);
		editor.commit();
	}

	public String getLoadingImg() {
		return sp.getString("loading", "");
	}

	public void setIsPromptWhenNoWIFI(boolean isPrompt) {
		editor.putBoolean("isPrompt", isPrompt);
		editor.commit();
	}

	public boolean getIsPromptWhenNoWIFI() {
		return sp.getBoolean("isPrompt", true);
	}

	public void setIsLogin(boolean flag) {
		editor.putBoolean("isLogin", true);
		editor.commit();
	}

	public boolean getIsLogin() {
		return sp.getBoolean("isLogin", false);
	}

	public void setIsThirdLogin(boolean flag) {
		editor.putBoolean("isThird", flag);
		editor.commit();
	}

	public boolean getIsThirdLogin() {
		return sp.getBoolean("isThird", false);
	}

	public void setPlatName(String platName) {
		editor.putString("platName", platName);
		editor.commit();
	}

	public String getPlatName() {
		return sp.getString("platName", "");
	}

	public void setBirthday(String birthday) {
		editor.putString("birthday", birthday);
		editor.commit();
	}

	public String getBirthday() {
		return sp.getString("birthday", "");
	}

	public void setPersonDesc(String desc) {
		editor.putString("desc", desc);
		editor.commit();
	}

	public String getPersonDesc() {
		return sp.getString("desc", "");
	}

	public void putString(String key, String value) {
		editor.putString(key, value);
	}

	public void putInt(String key, int value) {
		editor.putInt(key, value);
	}

	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
	}

	public void putLong(String key, long value) {
		editor.putLong(key, value);
	}

	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
	}

	public void putIsVote(String key, boolean value) {
		editor.putBoolean(key, value);
	}

	public int getInt(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public float getFloat(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public long getLong(String key, long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

	public String getString(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public void commit() {
		editor.commit();
	}

	public void clear() {
		editor.clear();
		editor.commit();
	}
}
