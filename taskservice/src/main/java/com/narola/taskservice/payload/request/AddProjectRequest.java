package com.narola.taskservice.payload.request;

public class AddProjectRequest {

	private String projectName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "AddProjectRequest [projectName=" + projectName + "]";
	}

}
