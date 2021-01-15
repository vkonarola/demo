package com.narola.taskservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.core.task.constant.TaskStatus;
import com.narola.taskservice.payload.dto.TaskDTO;
import com.narola.taskservice.payload.request.UpdateTaskRequest;

@Component("update_task_request_validator")
public class UpdateTaskRequestValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdateTaskRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("UpdateTaskRequest:{}", target);
		UpdateTaskRequest taskRequest = (UpdateTaskRequest) target;
		if (ObjectUtils.isEmpty(taskRequest.getTaskList())) {
			errors.rejectValue("taskList", "taskList.empty");
			return;
		}
		for (TaskDTO taskDTO : taskRequest.getTaskList()) {
			if (ObjectUtils.isEmpty(taskDTO.getTaskId())) {
				errors.reject("taskdto.taskId.empty");
				return;
			}
			if (taskDTO.getTaskId().intValue() <= 0) {
				errors.reject("taskdto.taskId.invalid");
				return;
			}
			// @formatter:off		
			if (ObjectUtils.isEmpty(taskDTO.getTaskDesc()) && 
				ObjectUtils.isEmpty(taskDTO.getEstHr()) && 
				ObjectUtils.isEmpty(taskDTO.getActualHr()) &&
				ObjectUtils.isEmpty(taskDTO.getStatus()) &&
				ObjectUtils.isEmpty(taskDTO.getIsTrackerUsed())) {
			// @formatter:on
				errors.reject("taskdto.allfields.empty");
				return;
			}
			if (!ObjectUtils.isEmpty(taskDTO.getEstHr()) && taskDTO.getEstHr().doubleValue() <= 0.0) {
				errors.reject("taskdto.esthr.invalid");
				return;
			}
			if (!ObjectUtils.isEmpty(taskRequest.getIsEodUpdate()) && taskRequest.getIsEodUpdate()) {
				if (ObjectUtils.isEmpty(taskDTO.getStatus())) {
					errors.reject("taskdto.status.empty");
					return;
				}
				if (ObjectUtils.isEmpty(taskDTO.getIsTrackerUsed())) {
					errors.reject("taskdto.isTrackerUsed.empty");
					return;
				}
			}
			if (!ObjectUtils.isEmpty(taskDTO.getStatus())) {
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
			}
		}
	}
}