package com.narola.taskservice.payload.request;

public class UpdateProjectRequest {

	private Integer projectId;
	private String projectName;

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

	@Override
	public String toString() {
		return "UpdateProjectRequest [projectId=" + projectId + ", projectName=" + projectName + "]";
	}

}
