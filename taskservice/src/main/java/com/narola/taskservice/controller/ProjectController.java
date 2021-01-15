package com.narola.taskservice.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narola.core.exception.BusinessException;
import com.narola.core.payload.response.APIResponse;
import com.narola.taskservice.payload.request.AddProjectRequest;
import com.narola.taskservice.payload.request.AddUserToProjectRequest;
import com.narola.taskservice.payload.request.UpdateProjectRequest;
import com.narola.taskservice.service.IProjectService;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	@Qualifier("add_project_request_validator")
	private Validator addProjectRequestValidator;

	@Autowired
	@Qualifier("addUserToProjectReqValidator")
	private Validator addUserToProjectReqValidator;

	@Autowired
	@Qualifier("updateProjectRequestValidator")
	private Validator updateProjectRequestValidator;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IProjectService projectService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> addProject(@RequestBody AddProjectRequest addProjectRequest,
			BindingResult result) throws Exception {
		this.addProjectRequestValidator.validate(addProjectRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getFieldError().getCode(), null, Locale.getDefault()));
		}
		this.projectService.addProject(addProjectRequest);
		return ResponseEntity.ok().body(new APIResponse());
	}

	@PostMapping(path = "/{projectId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> updateProject(@PathVariable int projectId,
			@RequestBody UpdateProjectRequest updateProjectRequest, BindingResult result) throws Exception {
		updateProjectRequest.setProjectId(projectId);
		this.updateProjectRequestValidator.validate(updateProjectRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getFieldError().getCode(), null, Locale.getDefault()));
		}
		this.projectService.updateProject(updateProjectRequest);
		return ResponseEntity.ok().body(new APIResponse());
	}

	@PostMapping(path = "/adduser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> addUserToProject(@RequestBody AddUserToProjectRequest addUserToProjReq,
			BindingResult result) throws Exception {
		this.addUserToProjectReqValidator.validate(addUserToProjReq, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getAllErrors().get(0).getCode(), null, Locale.getDefault()));
		}
		this.projectService.addUserToProject(addUserToProjReq);
		return ResponseEntity.ok().body(new APIResponse());
	}

	@GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> getProjectsForCurrentUser() {
		return ResponseEntity.ok().body(new APIResponse().setData(this.projectService.getProjectForCurrentUser()));
	}
}