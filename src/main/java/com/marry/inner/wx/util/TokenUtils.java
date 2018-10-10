package com.marry.inner.wx.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.marry.inner.wx.vo.AccessToken;
import com.marry.inner.wx.vo.JsApiTicket;


public class TokenUtils {
	/**
	 * 每个公众号的token对象 键值对
	 */
	public static Map<String, Map<String, Object>> tokenMap = new HashMap<String, Map<String, Object>>();
	
	
	/**
	 * 每个公众号的jsapi_ticket对象 键值对
	 */
	public static Map<String, Map<String, Object>> ticketMap = new HashMap<String, Map<String, Object>>();
	
	/**
	*@Author Rambo
	*@Desc：获取tokeId(同步锁)
	*@param agentid 应用唯一ID
	*@param appId 
	*@param appSecret
	*@return TODO
	*@Version  TokenUtils.java,v 1.1 2014-10-10 上午10:43:05 
	*/
	public synchronized static String checkTokenGZH(String appId,
			String appSecret, String agentid) {
		
		String key = appId;
		Map<String, Object> map = tokenMap.get(key);
		
		String tokenId = "";
		if (map == null) {
			map = new HashMap<String, Object>();
			Long beginTime = new Date().getTime();
			AccessToken accessToken = WeixinUtil.getAccessToken(appId, appSecret, agentid);
			tokenId = accessToken.getToken();
			map.put("tokenKey", accessToken);
			map.put("beginTime", beginTime);
			tokenMap.put(key, map);
		} else {

			AccessToken accessToken = (AccessToken) map.get("tokenKey");
			// 获取该token的时间(ms)
			Long beginTime = (Long) map.get("beginTime");

			// 获取现在时间(ms)
			Long nowTime = new Date().getTime();

			// 从获取该token时的时间到现在总共过去了多少秒(ms)
			Long timeLag = nowTime - beginTime;

			// 该token最长有效时间(s)
			int expiresTime = accessToken.getExpiresIn();

			
			boolean flag = (timeLag / 1000 - 200) > expiresTime;

			// 表示该token已经失效，需要再次获取新的token
			if (flag) {
				Long beginNewTime = new Date().getTime();
				accessToken = WeixinUtil.getAccessToken(appId, appSecret, agentid);
				map.put("tokenKey", accessToken);
				map.put("beginTime", beginNewTime);
			}
			tokenId = accessToken.getToken();
		}
		System.out.println("AccessToken:"+tokenId);
		return tokenId;
	}	
	
	public synchronized static String checkTicketAPI(String appId, String appSecret, String agentid) throws Exception {
		
		String key = appId;
		Map<String, Object> map = ticketMap.get(key);
		
		String ticketId = "";
		if (map == null) {
			map = new HashMap<String, Object>();
			Long beginTime = new Date().getTime();
			JsApiTicket ticket = WeiXinApiUtil.getJsapiTicket(appId, appSecret, agentid);
			ticketId = ticket.getTicket();
			map.put("ticketKey", ticket);
			map.put("beginTime", beginTime);
			ticketMap.put(key, map);
		} else {

			JsApiTicket ticket = (JsApiTicket) map.get("ticketKey");
			// 获取该token的时间(ms)
			Long beginTime = (Long) map.get("beginTime");

			// 获取现在时间(ms)
			Long nowTime = new Date().getTime();

			// 从获取该token时的时间到现在总共过去了多少秒(ms)
			Long timeLag = nowTime - beginTime;

			// 该token最长有效时间(s)
			int expiresTime = ticket.getExpires_in();

			
			boolean flag = (timeLag / 1000 - 200) > expiresTime;

			// 表示该token已经失效，需要再次获取新的token
			if (flag) {
				Long beginNewTime = new Date().getTime();
				ticket = WeiXinApiUtil.getJsapiTicket(appId, appSecret, agentid);
				map.put("ticketKey", ticket);
				map.put("beginTime", beginNewTime);
			}
			ticketId = ticket.getTicket();
		}
		System.out.println("JsApi_Ticket:"+ticketId);
		return ticketId;
	}	
}
