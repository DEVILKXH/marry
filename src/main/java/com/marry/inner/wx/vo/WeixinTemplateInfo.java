package com.marry.inner.wx.vo;

import java.util.Map;

public class WeixinTemplateInfo {

	private String touser;
	private String template_id;
	private String url;
	private Miniprogram miniprogram;
	private Map<String, WeixinTemplateData> data;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Miniprogram getMiniprogram() {
		return miniprogram;
	}
	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}
	public Map<String, WeixinTemplateData> getData() {
		return data;
	}
	public void setData(Map<String, WeixinTemplateData> data) {
		this.data = data;
	}
	
}
