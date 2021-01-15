package com.narola.authenticationservice.payload.request;

public class SignupRequest {

	private String email;
	private String password;
	private String userFullName;
	private Integer easyCollabUserId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public Integer getEasyCollabUserId() {
		return easyCollabUserId;
	}

	public void setEasyCollabUserId(Integer easyCollabUserId) {
		this.easyCollabUserId = easyCollabUserId;
	}

	@Override
	public String toString() {
		return "SignupRequest [email=" + email + ", userFullName=" + userFullName + ", easyCollabUserId="
				+ easyCollabUserId + "]";
	}
}
