package com.narola.taskservice.payload.request;

import java.util.List;

public class AddUserToProjectRequest {

	private Integer projectId;
	private List<Integer> userIds;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public List<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Integer> userIds) {
		this.userIds = userIds;
	}

	@Override
	public String toString() {
		return "AddUserToProjectRequest [projectId=" + projectId + ", userIds=" + userIds + "]";
	}

}
