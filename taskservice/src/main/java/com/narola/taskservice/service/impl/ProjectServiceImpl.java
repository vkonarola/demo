package com.narola.taskservice.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.narola.core.authentication.IAuthenticationFacade;
import com.narola.core.authentication.entity.User;
import com.narola.core.authentication.repository.UserRepository;
import com.narola.core.exception.BusinessException;
import com.narola.core.task.entity.Project;
import com.narola.taskservice.payload.dto.ProjectDTO;
import com.narola.taskservice.payload.request.AddProjectRequest;
import com.narola.taskservice.payload.request.AddUserToProjectRequest;
import com.narola.taskservice.payload.request.UpdateProjectRequest;
import com.narola.taskservice.repository.ProjectRepository;
import com.narola.taskservice.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService {

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public void addProject(AddProjectRequest addProjectRequest) {
		if (projectRepository.existsByProjectName(addProjectRequest.getProjectName())) {
			throw new BusinessException(this.messageSource.getMessage("project.name.exist",
					new Object[] { addProjectRequest.getProjectName() }, Locale.getDefault()));
		}
		Project project = new Project();
		project.setProjectName(addProjectRequest.getProjectName());
		project.setCreatedBy(new User(this.authenticationFacade.getCurrentUserId()));
		project.setCreatedOn(LocalDateTime.now());
		this.projectRepository.save(project);
	}

	@Override
	public void updateProject(UpdateProjectRequest updateProjReq) {
		// @formatter:off
		Project project = this.projectRepository.findById(updateProjReq.getProjectId())
				.orElseThrow(
						() -> new BusinessException(
							   this.messageSource.getMessage("project.id.notExist",
							   new Object[] { updateProjReq.getProjectId() }, Locale.getDefault()))); 
		// @formatter:on
		if (this.projectRepository.existsByProjectName(updateProjReq.getProjectName())) {
			throw new BusinessException(this.messageSource.getMessage("project.name.exist",
					new Object[] { updateProjReq.getProjectName() }, Locale.getDefault()));
		}

		project.setProjectName(updateProjReq.getProjectName());
		project.setUpdatedOn(LocalDateTime.now());
		this.projectRepository.save(project);
	}

	@Override
	public void addUserToProject(AddUserToProjectRequest addUserToProjReq) {
		Project project = this.projectRepository.findById(addUserToProjReq.getProjectId()).get();
		for (int userId : addUserToProjReq.getUserIds()) {
			Optional<User> opUser = project.getUsers().stream().filter((User u) -> u.getUserId() == userId).findFirst();
			if (opUser.isPresent()) {
				throw new BusinessException("User id:" + opUser.get().getUserId() + " is already added to project");
			}
			project.addUser(this.userRepository.getOne(userId));
		}
		this.projectRepository.save(project);
	}

	@Override
	public List<ProjectDTO> getProjectForCurrentUser() {
		/*
		 * This is temporary only for testing purpose if user doesn't have any assigned
		 * project then add user to all projects because newly created user don't have
		 * any project.
		 * 
		 * When admin panel available for adding user to any project remove this method
		 */
		addCurrentUserToAllProjectIfNotPresent();
		return getProject(this.authenticationFacade.getCurrentUserId());
	}

	private void addCurrentUserToAllProjectIfNotPresent() {
		try {
			List<ProjectDTO> dtos = getProject(this.authenticationFacade.getCurrentUserId());
			if (dtos == null || dtos.isEmpty()) {
				User user = this.userRepository.getOne(this.authenticationFacade.getCurrentUserId());
				List<Project> projects = this.projectRepository.findAll();
				for (Project project : projects) {
					project.addUser(user);
					this.projectRepository.save(project);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProjectDTO> getProject(int userId) {
		List<Project> projects = this.projectRepository.findByUsers_UserId(userId);
		List<ProjectDTO> dtos = new ArrayList<>(projects.size());
		// TODO: replace for loop with stream map function
		for (Project project : projects) {
			ProjectDTO dto = new ProjectDTO();
			dto.setProjectId(project.getId());
			dto.setProjectName(project.getProjectName());
			dtos.add(dto);
		}
		return dtos;
	}
}