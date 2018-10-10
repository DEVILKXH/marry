package com.marry.inner.wx.vo;

public class UserTicketInfo {

	private String DeviceId;
	
	private String UserId;

	private String errcode;
	
	private String errmsg;
	
	private int expires_in;
	
	private String user_ticket;
	
	private String accessToken;

	public String getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getUser_ticket() {
		return user_ticket;
	}

	public void setUser_ticket(String user_ticket) {
		this.user_ticket = user_ticket;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
