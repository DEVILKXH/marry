package com.marry.inner.wx.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.marry.inner.wx.constant.WeiXinWorkConstant;
import com.marry.inner.wx.vo.AccessToken;
import com.marry.inner.wx.vo.WeixinSendMesage;
import com.marry.inner.wx.vo.WeixinTemplateData;
import com.marry.inner.wx.vo.WeixinTemplateInfo;

public class WexinPushUtil {

	/**
	 * 发送模板信息
	 * @param openId
	 * @param docSubject
	 * @param tempUrl
	 */
	public static void sendTemplate(String openId, Map<String, WeixinTemplateData> data, String tempUrl, String templateId) {
		AccessToken accessToken = WeixinUtil.getAccessToken(WeiXinWorkConstant.APPID, WeiXinWorkConstant.SECRET, null);
		String url = WeiXinWorkConstant.CODE_TO_SEND_TEMPLATE.replace("ACCESS_TOKEN", accessToken.getToken());
		WeixinTemplateInfo template = new WeixinTemplateInfo();
		template.setTouser(openId);
		template.setTemplate_id(templateId);
		template.setUrl(tempUrl);
		template.setData(data);
		WeixinUtil.httpRequest(url, "POST", JSONObject.toJSONString(template));
	}
	
	/**
	 * 发送其他信息
	 * @param openId
	 * @param docSubject
	 * @param tempUrl
	 */
	public static void sendTemplateByIp(String openId, String docSubject, String tempUrl, String templateId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		AccessToken accessToken = WeixinUtil.getAccessToken(WeiXinWorkConstant.APPID, WeiXinWorkConstant.SECRET, null);
		String url = WeiXinWorkConstant.CODE_TO_SEND_TEMPLATE.replace("ACCESS_TOKEN", accessToken.getToken());
		WeixinTemplateInfo template = new WeixinTemplateInfo();
		template.setTouser(openId);
		template.setTemplate_id(templateId);
		template.setUrl(tempUrl);
		Map<String, WeixinTemplateData> data = new HashMap<String, WeixinTemplateData>();
		data.put("first", new WeixinTemplateData("当前IP[" + docSubject + "]", "#110000"));
		data.put("keyword1", new WeixinTemplateData(sdf.format(new Date()), "#000000"));
		data.put("keyword2", new WeixinTemplateData("IP", "#000000"));
		data.put("keyword3", new WeixinTemplateData("IP", "#000000"));
		data.put("keyword4", new WeixinTemplateData("", "#000000"));
		data.put("remark", new WeixinTemplateData("", "#000000"));
		template.setData(data);
		WeixinUtil.httpRequest(url, "POST", JSONObject.toJSONString(template));
	}
	
	/**
	 * 群发信息
	 * @param openId
	 * @param docSubject
	 * @param tempUrl
	 */
	public static JSONObject sendMessageForAll(String title, String content, String sourceUrl) {
		AccessToken accessToken = WeixinUtil.getAccessToken(WeiXinWorkConstant.APPID, WeiXinWorkConstant.SECRET, null);
		String uploadUrl = WeiXinWorkConstant.CODE_TO_UPLOAD_NEWS.replace("ACCESS_TOKEN", accessToken.getToken());
		JSONArray articles = new JSONArray();
		JSONObject article = new JSONObject();
		article.put("thumb_media_id", WeiXinWorkConstant.THUMB_MEDIA_ID);
		article.put("title", title);
		article.put("content", content);
		article.put("content_source_url", sourceUrl);
		
		articles.add(article);
		JSONObject data = new JSONObject();
		data.put("articles", articles);
		JSONObject res = WeixinUtil.httpRequest(uploadUrl, "POST", JSONObject.toJSONString(data));
		System.out.println(res);
		String url = WeiXinWorkConstant.CODE_TO_SEND_ALL.replace("ACCESS_TOKEN", accessToken.getToken());
		WeixinSendMesage message = new WeixinSendMesage(res.getString("media_id"));
		JSONObject result = WeixinUtil.httpRequest(url, "POST", JSONObject.toJSONString(message));
		return result;
	}
}
