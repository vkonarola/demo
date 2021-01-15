package com.narola.taskservice.payload.dto;

import java.time.LocalDateTime;

public class TaskDTO {

	// New
	private Integer projectId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	// FINISH

	private Integer taskId;
	private String taskDesc;
	private Double estHr;
	private Double actualHr;
	private Boolean isTrackerUsed;
	private Boolean isPlanned;
	private Boolean isEodUpdate;
	private Integer status;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;

	public Boolean getIsEodUpdate() {
		return isEodUpdate;
	}

	public void setIsEodUpdate(Boolean isEodUpdate) {
		this.isEodUpdate = isEodUpdate;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
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

	public Boolean getIsTrackerUsed() {
		return isTrackerUsed;
	}

	public void setIsTrackerUsed(Boolean isTrackerUsed) {
		this.isTrackerUsed = isTrackerUsed;
	}

	public Boolean getIsPlanned() {
		return isPlanned;
	}

	public void setIsPlanned(Boolean isPlanned) {
		this.isPlanned = isPlanned;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "TaskDTO [projectId=" + projectId + ", taskId=" + taskId + ", taskDesc=" + taskDesc + ", estHr=" + estHr
				+ ", actualHr=" + actualHr + ", isTrackerUsed=" + isTrackerUsed + ", isPlanned=" + isPlanned
				+ ", status=" + status + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
}