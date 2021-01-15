package com.narola.taskservice.payload.dto;

import java.util.List;

public class ProjectDTO {

	private Integer projectId;
	private String projectName;
	private List<TaskDTO> taskLists;
	private Boolean isEodUpdated;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<TaskDTO> getTaskLists() {
		return taskLists;
	}

	public void setTaskLists(List<TaskDTO> taskLists) {
		this.taskLists = taskLists;
	}

	public Boolean getIsEodUpdated() {
		return isEodUpdated;
	}

	public void setIsEodUpdated(Boolean isEodUpdated) {
		this.isEodUpdated = isEodUpdated;
	}

	@Override
	public String toString() {
		return "ProjectDTO [projectId=" + projectId + ", projectName=" + projectName + ", taskLists=" + taskLists
				+ ", isEodUpdated=" + isEodUpdated + "]";
	}
}