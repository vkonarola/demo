package com.narola.core.task.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.narola.core.authentication.entity.User;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "project_name", nullable = false, unique = true, length = 25)
	private String projectName;

	// @formatter:off
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name = "project_user", 
				joinColumns = { @JoinColumn(name = "project_id") }, 
				inverseJoinColumns = { @JoinColumn(name = "user_id") })
	// @formatter:on
	private Set<User> users = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@Column(name = "easy_collab_proj_id")
	private String easyCollabProjectId;

	@Column(name = "easy_collab_task_id")
	private String easyCollabTaskId;

	@Column(name = "createdon", nullable = false)
	private LocalDateTime createdOn;

	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

	public String getEasyCollabProjectId() {
		return easyCollabProjectId;
	}

	public void setEasyCollabProjectId(String easyCollabProjectId) {
		this.easyCollabProjectId = easyCollabProjectId;
	}

	public String getEasyCollabTaskId() {
		return easyCollabTaskId;
	}

	public void setEasyCollabTaskId(String easyCollabTaskId) {
		this.easyCollabTaskId = easyCollabTaskId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Project addUser(User user) {
		users.add(user);
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + "]";
	}
}