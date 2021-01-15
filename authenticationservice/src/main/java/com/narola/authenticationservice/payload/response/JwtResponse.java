package com.narola.authenticationservice.payload.response;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String userFullName;

	public JwtResponse(String accessToken, String username, String userFullName) {
		this.token = accessToken;
		this.username = username;
		this.userFullName = userFullName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
}