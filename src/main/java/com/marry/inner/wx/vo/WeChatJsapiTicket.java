package com.marry.inner.wx.vo;

public class WeChatJsapiTicket {

	private String ticket;
	// 生成时间
	private long fdBeginTime;

	// 凭证有效时间，单位：秒
	private int fdExpiresIn;

	public long getFdBeginTime() {
		return fdBeginTime;
	}

	public void setFdBeginTime(long fdBeginTime) {
		this.fdBeginTime = fdBeginTime;
	}

	public int getFdExpiresIn() {
		return fdExpiresIn;
	}

	public void setFdExpiresIn(int fdExpiresIn) {
		this.fdExpiresIn = fdExpiresIn;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
