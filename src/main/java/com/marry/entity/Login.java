package com.marry.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.marry.inner.base.entity.BaseEntity;

@Table(name = "LOGIN")
public class Login extends BaseEntity{

	@Column(name = "LOGIN_TIME")
    private Date loginTime;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}