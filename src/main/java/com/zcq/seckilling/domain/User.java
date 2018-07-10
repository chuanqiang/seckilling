package com.zcq.seckilling.domain;

import java.io.Serializable;

public class User implements Serializable{
	private Integer id;
	private String name;
	private String passWord;
	public String getSalt;

	public User() {
	}

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getGetSalt() {
		return getSalt;
	}

	public void setGetSalt(String getSalt) {
		this.getSalt = getSalt;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
