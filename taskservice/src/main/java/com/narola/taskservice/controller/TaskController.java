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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narola.core.exception.BusinessException;
import com.narola.core.payload.response.APIResponse;
import com.narola.taskservice.payload.request.AddTaskRequest;
import com.narola.taskservice.payload.request.AddUpdateTaskRequest;
import com.narola.taskservice.payload.request.UpdateTaskRequest;
import com.narola.taskservice.service.ITaskService;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private ITaskService taskService;

	@Autowired
	@Qualifier("add_task_request_validator")
	private Validator addTaskRequestValidator;

	@Autowired
	@Qualifier("update_task_request_validator")
	private Validator updateTaskRequestValidator;

	@Autowired
	@Qualifier("add__update_task_request_validator")
	private Validator addUpdateTaskRequestValidator;

	@Autowired
	private MessageSource messageSource;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> addTask(@RequestBody AddTaskRequest addTaskRequest, BindingResult result)
			throws Exception {
		this.addTaskRequestValidator.validate(addTaskRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getAllErrors().get(0).getCode(), null, Locale.getDefault()));
		}
		return ResponseEntity.ok().body(new APIResponse().setData(this.taskService.addTask(addTaskRequest)));
	}

	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> updateTask(@RequestBody UpdateTaskRequest updateTaskRequest,
			BindingResult result) throws Exception {
		this.updateTaskRequestValidator.validate(updateTaskRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getAllErrors().get(0).getCode(), null, Locale.getDefault()));
		}
		this.taskService.updateTask(updateTaskRequest);
		return ResponseEntity.ok().body(new APIResponse());
	}

	@PostMapping(path = "/addupdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> addUpdateTask(@RequestBody AddUpdateTaskRequest addUpdateTaskRequest,
			BindingResult result) throws Exception {
		this.addUpdateTaskRequestValidator.validate(addUpdateTaskRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(this.messageSource.getMessage(result.getAllErrors().get(0).getCode(),
					result.getAllErrors().get(0).getArguments(), Locale.getDefault()));
		}
		return ResponseEntity.ok()
				.body(new APIResponse().setData(this.taskService.addUpdateTask(addUpdateTaskRequest)));
	}

	@GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> getAllTaskForCurrentUser() throws Exception {
		return ResponseEntity.ok().body(new APIResponse().setData(this.taskService.getAllTask()));
	}

	@GetMapping(path = "/send-dailytask-updatemail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> submitDailyTaskReport() throws Exception {
		this.taskService.sendDailyTaskUpdateMail();
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/submit-task-to-easycollab-timesheet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> submitDailyTaskReportToEasyCollab() throws Exception {
		this.taskService.submitEasyCollabTimesheetData();
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResponse> deleteTask() {
		this.taskService.deleteTask();
		return ResponseEntity.ok().build();
	}
}