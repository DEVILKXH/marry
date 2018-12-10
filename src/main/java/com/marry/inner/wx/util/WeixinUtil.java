package com.marry.inner.wx.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.marry.inner.wx.constant.APIConstant;
import com.marry.inner.wx.constant.WeiXinWorkConstant;
import com.marry.inner.wx.vo.AccessToken;
import com.marry.inner.wx.vo.UserInfo;
import com.marry.inner.wx.vo.UserTicketInfo;
import com.marry.inner.wx.vo.WeChatJsapiTicket;
import com.marry.inner.wx.vo.WeixinUser;


/**
 * 公众平台通用接口工具类
 * 
 * @author fivest
 * @date 2013-08-09
 */
public class WeixinUtil {
	public final static String MP_ACCESS_TOKEN_URL_GET = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static final String CP_ACCESS_TOKEN_URL_GET = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSON.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection error.",ce);
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret,
			String agentid) {
		AccessToken accessToken = null;
		String access_token_url = "";
		String requestUrl = "";
		if (StringUtils.isEmpty(agentid)) {
			access_token_url = APIConstant.MP_ACCESS_TOKEN_GET.getUrl();
			requestUrl = access_token_url.replace("APPID", appid).replace(
					"APPSECRET", appsecret);
		} else {
			access_token_url = APIConstant.QY_ACCESS_TOKEN_GET.getUrl();
			requestUrl = access_token_url.replace("CORPID", appid).replace(
					"CORPSECRET", appsecret);
		}

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(7200);
			} catch (JSONException e) {
				
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println("获取token失败 errcode:{} errmsg:{}"
						+ jsonObject.getInteger("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		System.out.println("获取token成功:" + accessToken.getToken());
		return accessToken;
	}

	

	
	
	/**
	 * 获取weChatJsapiTicket
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static WeChatJsapiTicket getWeChatJsapiTicket(String accesstoken) {
		WeChatJsapiTicket weChatJsapiTicket = null;
		String requestUrl = "";
		String jsapi_ticket_url ="";
		if (StringUtils.isEmpty(WeiXinWorkConstant.APPID)) {
			jsapi_ticket_url =APIConstant.MP_JSAPI_TICKET.getUrl();
		}else{
			jsapi_ticket_url =APIConstant.QY_JSAPI_TICKET.getUrl();
		}
		requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", accesstoken);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weChatJsapiTicket = new WeChatJsapiTicket();
				Long beginTime = new Date().getTime();
				weChatJsapiTicket.setFdBeginTime(beginTime);
				weChatJsapiTicket.setFdExpiresIn(7200);
				weChatJsapiTicket.setTicket(jsonObject.getString("ticket"));
			} catch (JSONException e) {
				weChatJsapiTicket = null;
				// 获取weChatJsapiTicket失败
				log.error("获取weChatJsapiTicket失败 errcode:{} errmsg:{}",
						jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
				System.out.println("获取weChatJsapiTicket失败 errcode:{} errmsg:{}"
						+ jsonObject.getInteger("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		System.out.println("获取weChatJsapiTicket成功:" + weChatJsapiTicket.getTicket());
		return weChatJsapiTicket;
	}

	/**
	 * 创建菜单 lizh
	 * 
	 * @param jsonMenu
	 *            自定义菜单内容
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 * 
	 */
	public static int createMenu(String jsonMenu, String accessToken,
			String agentid) {
		String url = "";
		String menu_create_url = "";
		if (StringUtils.isEmpty(agentid)) {
			menu_create_url = APIConstant.MP_MEMU_CREATE.getUrl();
			// 拼装创建菜单的url
			url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		} else {
			menu_create_url = APIConstant.QY_MEMU_CREATE.getUrl();
			url = menu_create_url.replace("ACCESS_TOKEN", accessToken).replace(
					"AGENTID", agentid);
		}

		int result = 0;

		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInteger("errcode")) {
				result = jsonObject.getInteger("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}
	
	public static UserTicketInfo getTicket(String appid, String appsecret, String agentid, String code) {
		AccessToken accessToken = getAccessToken(appid, appsecret, agentid);
		String url = WeiXinWorkConstant.CODE_TO_USERINFO.replace("ACCESS_TOKEN", accessToken.getToken());
		url = url.replace("CODE", code);
		JSONObject object = httpRequest(url, "GET", null);
		UserTicketInfo ticket = JSONObject.toJavaObject(object, UserTicketInfo.class);
		ticket.setAccessToken(accessToken.getToken());
		System.out.println("获取ticket成功:" + ticket.getUser_ticket());
		return ticket;
	}
	
	public static UserInfo getUserInfo(String appid, String appsecret, String agentid, String code) {
		UserTicketInfo ticket = getTicket(appid, appsecret, agentid, code);
		String url = WeiXinWorkConstant.CODE_TO_TICKET.replace("ACCESS_TOKEN", ticket.getAccessToken());
		JSONObject param = new JSONObject();
		param.put("user_ticket", ticket.getUser_ticket());
		JSONObject json = httpRequest(url, "POST", param.toJSONString());
		UserInfo userInfo = JSONObject.toJavaObject(json, UserInfo.class);
		System.out.println("获取用户信息成功:" + json);
		return userInfo;
	}

	public static WeixinUser getOpenId(String code) {
		String url = WeiXinWorkConstant.CODE_TO_OPENID.replace("APPID", WeiXinWorkConstant.APPID).replace("SECRET", WeiXinWorkConstant.SECRET).replace("CODE", code);
		JSONObject json = httpRequest(url, "GET", null);
		WeixinUser user = JSONObject.toJavaObject(json, WeixinUser.class);
		return user;
	}
	
	public static UserInfo getUserInfo(String code) {
		WeixinUser user = getOpenId(code);
		String url = WeiXinWorkConstant.CODE_TO_USERINFO.replace("ACCESS_TOKEN", user.getAccess_token()).replace("ACCESS_TOKEN", user.getOpenid());
		JSONObject json = httpRequest(url, "GET", null);
		return JSONObject.toJavaObject(json, UserInfo.class);
	}
}


	