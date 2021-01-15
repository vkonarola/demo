package com.narola.taskservice.service;

import java.util.List;

import com.narola.taskservice.payload.dto.ProjectDTO;
import com.narola.taskservice.payload.request.AddProjectRequest;
import com.narola.taskservice.payload.request.AddUserToProjectRequest;
import com.narola.taskservice.payload.request.UpdateProjectRequest;

public interface IProjectService {

	void addProject(AddProjectRequest addProjectRequest);

	void updateProject(UpdateProjectRequest updateProjectRequest);

	void addUserToProject(AddUserToProjectRequest addUserToProjReq);

	List<ProjectDTO> getProjectForCurrentUser();

	List<ProjectDTO> getProject(int userId);

}
