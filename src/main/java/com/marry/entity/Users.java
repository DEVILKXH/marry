package com.marry.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.marry.inner.base.entity.BaseEntity;

@Table(name = "USERS")
public class Users extends BaseEntity{

	@Column(name = "USER_CODE")
    private String userCode;

	@Column(name = "USER_NAME")
    private String userName;

	@Column(name = "PASSWORD")
    private String password;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}