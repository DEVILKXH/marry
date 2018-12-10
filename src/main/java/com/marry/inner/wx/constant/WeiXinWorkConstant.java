package com.marry.inner.wx.constant;

public class WeiXinWorkConstant {

	public final static String APPID = "wx3a16bc62781e0418";
	
	public final static String SECRET = "627c6cb1cfffedb98e1b28587c0e055a";

	public final static String TEMPLATE_TO_MANAGER = "9RStbULyIVUItNN5UpDh9aPhV3ftW6rlW8EXJbFLlqU";

	public final static String TEMPLATE_TO_SERVICE = "cx8rKefcR0astZMj9aUu56ix8cBzpfnPIoQBQ1c2ZG4";
	
	public final static String TEMPLATE_TO_FEEDBACK = "uzOCN17YHduIRW4FQI9JtYNeJWGyrGCJ9xh198NBj3E";

	public final static String TEMPLATE_TO_ORDER_STATUS = "7Dy6UAFU1nI-P274DikMCo5FhBa423iBvXc5prwp9J8";
	
	public final static String THUMB_MEDIA_ID = "yytG9mRlcDWWV-j72alWQjkk-Tnm7D8-emzWdd706jp9O_tecRFwHnTDQpQfTqEE";
	
	public final static String CODE_TO_TICKET =  "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";
	
	public final static String CODE_TO_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	public final static String CODE_TO_SEND = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

	public final static String CODE_TO_SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	public final static String CODE_TO_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	public final static String CODE_TO_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=ACCESS_TOKEN&lang=zh_CN";
	
	public final static String CODE_TO_SEND_ALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";

	public final static String CODE_TO_UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
}

