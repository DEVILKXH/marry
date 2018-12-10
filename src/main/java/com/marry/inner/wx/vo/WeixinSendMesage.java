package com.marry.inner.wx.vo;

public class WeixinSendMesage {

	private Filter filter;
	
	private Text text;
	
	private MpNews mpnews;
	
	private String msgtype;

	private int send_ignore_reprint = 1;
	
	public WeixinSendMesage(String mediaId) {
		this.filter = new Filter();
		this.mpnews = new MpNews(mediaId);
		this.msgtype = "mpnews";
	}
	
	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public MpNews getMpnews() {
		return mpnews;
	}

	public void setMpnews(MpNews mpnews) {
		this.mpnews = mpnews;
	}

	public int getSend_ignore_reprint() {
		return send_ignore_reprint;
	}

	public void setSend_ignore_reprint(int send_ignore_reprint) {
		this.send_ignore_reprint = send_ignore_reprint;
	}
}
