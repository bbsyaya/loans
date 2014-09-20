
package com.will.loans.constant;

public class ServerInfo {
	public static final String IMAGE_PREFIX = "http://daidaitong.imwanmei.com:8080/mobile/";

	public static final String BANNER = IMAGE_PREFIX + "front/bannerinfo.do";
	/**
	 * 登录前置接口 - 输入手机号判断密码登录还是短信登录
	 */
	public static final String LOGINFIRST = IMAGE_PREFIX + "loginFirst";
	/**
	 * 密码登录接口
	 */
	public static final String LOGINBYPSW = IMAGE_PREFIX + "loginByPsw";
	/**
	 * 注册及短信登录接口
	 */
	public static final String REGISTERORLOGINBYMSG = IMAGE_PREFIX + "registerOrLoginByMsg";
	/**
	 * 退出登录接口
	 */
	public static final String LOGOUT = IMAGE_PREFIX + "logout";
	/**
	 * 用户个人信息接口
	 */
	public static final String USERMSG = IMAGE_PREFIX + "userMsg";
	/**
	 * 查询个人消息接口
	 */
	public static final String MSGLIST = IMAGE_PREFIX + "msgList";
	/**
	 * 查询产品信息接口
	 */
	public static final String PROLIST = IMAGE_PREFIX + "proList";
	/**
	 * 查询产品详细信息接口
	 */
	public static final String PRODETAIL = IMAGE_PREFIX + "proDetail";
	/**
	 * 查询用户当天收益接口
	 */
	public static final String TODAYPROFIT = IMAGE_PREFIX + "todayProfit";
	/**
	 * 产查询个人交易记录接口
	 */
	public static final String TRADERECLIST = IMAGE_PREFIX + "tradeRecList";
	/**
	 * 查询银联支付结果接口
	 */
	public static final String QUERYUPMP = IMAGE_PREFIX + "queryUpmp";
	/**
	 * 设置或修改用户登录密码
	 */
	public static final String SETLOGINPSW = IMAGE_PREFIX + "setLoginPsw";
	/**
	 * 设置或修改用户交易密码
	 */
	public static final String SETTRADEPSW = IMAGE_PREFIX + "setTradePsw";
	/**
	 * 实名制验证接口
	 */
	public static final String CHECKREALNAME = IMAGE_PREFIX + "checkRealName";
	/**
	 * 预估收益接口
	 */
	public static final String ESTIMATEPROFIT = IMAGE_PREFIX + "estimateProfit";

}
