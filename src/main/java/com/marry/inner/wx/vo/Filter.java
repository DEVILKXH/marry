package com.marry.inner.wx.vo;

public class Filter {
	public boolean is_to_all;
	
	public String tag_id;

	public Filter() {
		this.is_to_all = true;
	}
	
	public boolean getIs_to_all() {
		return is_to_all;
	}

	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}
	
}
