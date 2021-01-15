package com.narola.core.authentication.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "email_id", nullable = false, unique = true, length = 50)
	private String emailId;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "is_thirdparty_user", nullable = false)
	private Boolean isThirdPartyUser;

	@Column(name = "easy_collab_Id")
	private Integer easyCollabUserId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Column(name = "createdon", nullable = false)
	private LocalDateTime createdOn;

	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsThirdPartyUser() {
		return isThirdPartyUser;
	}

	public void setIsThirdPartyUser(Boolean isThirdPartyUser) {
		this.isThirdPartyUser = isThirdPartyUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getEasyCollabUserId() {
		return easyCollabUserId;
	}

	public void setEasyCollabUserId(Integer easyCollabUserId) {
		this.easyCollabUserId = easyCollabUserId;
	}
}
