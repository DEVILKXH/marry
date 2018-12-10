package com.marry.inner.wx.vo;

public class WeixinTemplateData {

	private String value;
	
	private String color;

	public WeixinTemplateData(String value, String color) {
		this.value = value;
		this.color = color;
	}
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
