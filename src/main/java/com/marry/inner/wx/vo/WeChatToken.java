package com.marry.inner.wx.vo;

public class WeChatToken {

	private String fdCode;
	
	private String fdAgentid;
	
	private String fdToken;
	
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

	public String getFdToken() {
		return fdToken;
	}

	public void setFdToken(String fdToken) {
		this.fdToken = fdToken;
	}
	
	
}
