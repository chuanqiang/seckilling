package com.zcq.seckilling.domain;

import com.zcq.seckilling.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class LoginVo {

	@NotNull
	@IsMobile
	private String mobile;

	@NotNull
	@Length(min = 16)
	private String password;

	public LoginVo() {
	}

	public LoginVo(String mobile, String password) {
		this.mobile = mobile;
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginVo{" +
				"mobile='" + mobile + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
