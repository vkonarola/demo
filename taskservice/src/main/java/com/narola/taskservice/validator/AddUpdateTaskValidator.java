package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.core.task.constant.TaskStatus;
import com.narola.taskservice.payload.dto.TaskDTO;
import com.narola.taskservice.payload.request.AddUpdateTaskRequest;

@Component("add__update_task_request_validator")
public class AddUpdateTaskValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return AddUpdateTaskRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("AddTaskRequest:{}", target);
		AddUpdateTaskRequest request = (AddUpdateTaskRequest) target;
		if (ObjectUtils.isEmpty(request.getTaskList())) {
			errors.rejectValue("taskList", "taskList.empty");
			return;
		}
		for (TaskDTO taskDTO : request.getTaskList()) {
			if (ObjectUtils.isEmpty(taskDTO.getProjectId())) {
				errors.reject("taskdto.projectId.empty");
				return;
			}
			if (taskDTO.getProjectId().intValue() <= 0) {
				errors.reject("taskdto.projectId.invalid");
				return;
			}
			if (!ObjectUtils.isEmpty(taskDTO.getTaskId()) && taskDTO.getTaskId().intValue() < 0) {
				errors.reject("taskdto.taskId.invalid");
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
			if (request.getIsEodUpdate() != null && request.getIsEodUpdate()) {
				if (ObjectUtils.isEmpty(taskDTO.getStatus())) {
					errors.reject("taskdto.status.empty");
					return;
				}
				if (!TaskStatus.isValidStatusCode(taskDTO.getStatus().intValue())) {
					errors.reject("taskdto.status.invalid");
					return;
				}
				if (taskDTO.getStatus().intValue() == TaskStatus.COMPLETED.getStatusCode()) {
					if (ObjectUtils.isEmpty(taskDTO.getActualHr())) {
						errors.reject("taskdto.actualhr.empty");
						return;
					}
					if (taskDTO.getActualHr().doubleValue() <= 0.0) {
						errors.reject("taskdto.actualhr.required", new Object[] { TaskStatus.COMPLETED.name() }, null);
						return;
					}
				} else if (!ObjectUtils.isEmpty(taskDTO.getActualHr()) && taskDTO.getActualHr().doubleValue() < 0.0) {
					errors.reject("taskdto.actualhr.invalid");
					return;
				}
				if (ObjectUtils.isEmpty(taskDTO.getIsTrackerUsed())) {
					errors.reject("taskdto.isTrackerUsed.empty");
					return;
				}
			}
		}
	}
}