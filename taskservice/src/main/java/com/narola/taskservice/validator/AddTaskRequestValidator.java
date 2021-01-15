package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.taskservice.payload.dto.TaskDTO;
import com.narola.taskservice.payload.request.AddTaskRequest;

@Component("add_task_request_validator")
public class AddTaskRequestValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return AddTaskRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("AddTaskRequest:{}", target);
		AddTaskRequest addTaskRequest = (AddTaskRequest) target;
		if (ObjectUtils.isEmpty(addTaskRequest.getTaskList())) {
			errors.rejectValue("taskList", "taskList.empty");
			return;
		}
		for (TaskDTO taskDTO : addTaskRequest.getTaskList()) {
			if (ObjectUtils.isEmpty(taskDTO.getProjectId())) {
				errors.reject("taskdto.projectId.empty");
				return;
			}
			if (taskDTO.getProjectId().intValue() <= 0) {
				errors.reject("taskdto.projectId.invalid");
				return;
			}
			if (!StringUtils.hasText(taskDTO.getTaskDesc())) {
				errors.reject("taskdto.taskDesc.empty");
				return;
			}
			if (ObjectUtils.isEmpty(taskDTO.getEstHr())) {
				errors.reject("taskdto.estHr.empty");
				return;
			}
			if (taskDTO.getEstHr().doubleValue() <= 0.0) {
				errors.reject("taskdto.esthr.invalid");
				return;
			}
		}
	}
}