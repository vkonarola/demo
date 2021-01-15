package com.narola.core.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetail extends User {

	private static final long serialVersionUID = 124446774502092677L;

	private int userId;
	private int easyCollabUserId;
	private String userFullName;

	public CustomUserDetail(int userId, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.setUserId(userId);
	}

	public CustomUserDetail(int userId, int easyCollabUserId, String username, String password, String userFullName,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.setUserId(userId);
		this.setUserFullName(userFullName);
		this.setEasyCollabUserId(easyCollabUserId);
	}

	public CustomUserDetail(int userId, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.setUserId(userId);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public int getEasyCollabUserId() {
		return easyCollabUserId;
	}

	public void setEasyCollabUserId(int easyCollabUserId) {
		this.easyCollabUserId = easyCollabUserId;
	}
}
