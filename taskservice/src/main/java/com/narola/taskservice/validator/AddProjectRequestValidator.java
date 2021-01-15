package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.taskservice.payload.request.AddProjectRequest;

@Component("add_project_request_validator")
public class AddProjectRequestValidator implements Validator {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return AddProjectRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("AddProjectRequest:{}", target);
		AddProjectRequest addProjectRequest = (AddProjectRequest) target;
		if (!StringUtils.hasText(addProjectRequest.getProjectName())) {
			errors.rejectValue("projectName", "project.name.empty");
		}
	}
}
