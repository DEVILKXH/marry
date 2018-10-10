package com.marry.inner.wx.vo;

/**
 * @author Administrator
 * 微信js-sdk票据 by wuhengbo@joeone.net 2016-01-27
 */
public class WeChatTicket {

	private String fdCode;
	
	private String fdAgentid;
	
	private String fdTicket;
	
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

	public String getFdCode() {
		return fdCode;
	}

	public void setFdCode(String fdCode) {
		this.fdCode = fdCode;
	}

	public String getFdAgentid() {
		return fdAgentid;
	}

	public void setFdAgentid(String fdAgentid) {
		this.fdAgentid = fdAgentid;
	}

	public String getFdTicket() {
		return fdTicket;
	}

	public void setFdTicket(String fdTicket) {
		this.fdTicket = fdTicket;
	}


	
	
}
