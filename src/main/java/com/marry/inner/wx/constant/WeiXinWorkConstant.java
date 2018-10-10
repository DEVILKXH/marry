package com.marry.inner.wx.constant;

public class WeiXinWorkConstant {

	public final static String APPID = "wx3a16bc62781e0418";
	
	public final static String SECRET = "733d5fb10f7046122f759de5a4bd99c3";
	
	public final static String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

	public final static String CODE_TO_TICKET =  "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";
	
	public final static String CODE_TO_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public final static String CODE_TO_SEND = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
}

