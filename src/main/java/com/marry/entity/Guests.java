package com.marry.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.marry.inner.base.entity.BaseEntity;

/**
 * 
 * @author Devil
 * TODO 宾客回复
 */
@Table(name = "GUESTS")
public class Guests extends BaseEntity{

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CONGRATULATION")
	private String congratulation;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "NUM")
	private String num;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCongratulation() {
		return congratulation;
	}

	public void setCongratulation(String congratulation) {
		this.congratulation = congratulation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
}
