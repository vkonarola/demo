package com.narola.core.task.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.narola.core.authentication.entity.User;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "task_desc", nullable = false)
	private String task_desc;

	@Column(name = "est_hr", nullable = false)
	private Double estHr;

	@Column(name = "actual_hr")
	private Double actualHr;

	@Column(name = "status")
	private Integer status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "is_planned", nullable = false)
	private Boolean isPlanned;

	@Column(name = "is_traker_used", nullable = false, columnDefinition = "BIT(1) default 0")
	private Boolean isTrackerUsed;

	@Column(name = "is_eod_update", nullable = false, columnDefinition = "BIT(1) default 0")
	private Boolean isEodUpdate;

	@Column(name = "is_submitted_to_easycollab", nullable = false, columnDefinition = "BIT(1) default 0")
	private Boolean isSubmittedToEasyCollab;

	@Column(name = "createdon", nullable = false)
	private LocalDateTime createdOn;

	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

	public Boolean getIsSubmittedToEasyCollab() {
		return isSubmittedToEasyCollab;
	}

	public void setIsSubmittedToEasyCollab(Boolean isSubmittedToEasyCollab) {
		this.isSubmittedToEasyCollab = isSubmittedToEasyCollab;
	}

	public Boolean getIsEodUpdate() {
		return isEodUpdate;
	}

	public void setIsEodUpdate(Boolean isEodUpdate) {
		this.isEodUpdate = isEodUpdate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTask_desc() {
		return task_desc;
	}

	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}

	public Double getEstHr() {
		return estHr;
	}

	public void setEstHr(Double estHr) {
		this.estHr = estHr;
	}

	public Double getActualHr() {
		return actualHr;
	}

	public void setActualHr(Double actualHr) {
		this.actualHr = actualHr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsPlanned() {
		return isPlanned;
	}

	public void setIsPlanned(Boolean isPlanned) {
		this.isPlanned = isPlanned;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Boolean getIsTrackerUsed() {
		return isTrackerUsed;
	}

	public void setIsTrackerUsed(Boolean isTrackerUsed) {
		this.isTrackerUsed = isTrackerUsed;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + "]";
	}
}