package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.taskservice.payload.request.UpdateProjectRequest;

@Component("updateProjectRequestValidator")
public class UpdateProjectReqValidator implements Validator {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateProjectRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("UpdateProjectRequest:{}", target);
		UpdateProjectRequest request = (UpdateProjectRequest) target;
		if (ObjectUtils.isEmpty(request.getProjectId()) || request.getProjectId().intValue() == 0) {
			errors.rejectValue("projectId", "updateProjReq.projectId.empty");
			return;
		}
		if (!StringUtils.hasText(request.getProjectName())) {
			errors.rejectValue("projectName", "updateProjReq.projectName.empty");
		}
	}

}
