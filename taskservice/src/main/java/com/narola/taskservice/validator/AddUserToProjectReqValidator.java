package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.taskservice.payload.request.AddUserToProjectRequest;

@Component("addUserToProjectReqValidator")
public class AddUserToProjectReqValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return AddUserToProjectRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("AddUserToProjectRequest:{}", target);
		AddUserToProjectRequest addUserToProjectReq = (AddUserToProjectRequest) target;
		if (ObjectUtils.isEmpty(addUserToProjectReq.getProjectId())
				|| addUserToProjectReq.getProjectId().intValue() == 0) {
			errors.rejectValue("projectId", "addUserToProjReq.projectId.empty");
			return;
		}
		if (ObjectUtils.isEmpty(addUserToProjectReq.getUserIds())) {
			errors.rejectValue("userIds", "addUserToProjReq.userIds.empty");
			return;
		}
		for (Integer userId : addUserToProjectReq.getUserIds()) {
			if (userId.intValue() == 0) {
				errors.reject("addUserToProjReq.userId.invalid");
				return;
			}
		}
	}
}