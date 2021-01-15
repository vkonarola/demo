package com.narola.taskservice.service.impl;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.narola.core.authentication.IAuthenticationFacade;
import com.narola.core.authentication.entity.User;
import com.narola.core.authentication.repository.UserRepository;
import com.narola.core.exception.BusinessException;
import com.narola.core.task.constant.TaskStatus;
import com.narola.core.task.easycollab.EasyCollabRESTService;
import com.narola.core.task.easycollab.payload.request.EasyCollabTimeSheet;
import com.narola.core.task.entity.Project;
import com.narola.core.task.entity.Task;
import com.narola.core.task.utility.DailyTaskStatusEmailTemplate;
import com.narola.core.task.utility.EmailService;
import com.narola.taskservice.payload.dto.TaskDTO;
import com.narola.taskservice.payload.request.AddTaskRequest;
import com.narola.taskservice.payload.request.AddUpdateTaskRequest;
import com.narola.taskservice.payload.request.UpdateTaskRequest;
import com.narola.taskservice.payload.response.GetTaskResponse;
import com.narola.taskservice.repository.ProjectRepository;
import com.narola.taskservice.repository.TaskRepository;
import com.narola.taskservice.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private IAuthenticationFacade authFacade;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EasyCollabRESTService easyCollabRESTService;

	@Override
	public GetTaskResponse addTask(AddTaskRequest addTaskRequest) {
		return new GetTaskResponse(convertToTaskDto(this.addUpdateTask(addTaskRequest.getTaskList(), Boolean.FALSE)));
	}

	@Override
	public void updateTask(UpdateTaskRequest updateTaskReq) throws Exception {
		List<Task> dbTaskList = new ArrayList<>();
		for (TaskDTO taskreq : updateTaskReq.getTaskList()) {
			Task dbtaskObj = this.taskRepository.getOne(taskreq.getTaskId());
			if (dbtaskObj.getIsEodUpdate()) {
				throw new BusinessException("EOD status already updated. Cann't update again.");
			}
			if (taskreq.getTaskDesc() != null && !taskreq.getTaskDesc().trim().isEmpty()) {
				dbtaskObj.setTask_desc(taskreq.getTaskDesc());
			}
			if (taskreq.getEstHr() != null) {
				dbtaskObj.setEstHr(taskreq.getEstHr());
			}
			if (taskreq.getActualHr() != null) {
				dbtaskObj.setActualHr(taskreq.getActualHr());
			}
			if (taskreq.getStatus() != null) {
				dbtaskObj.setStatus(taskreq.getStatus());
			}
			if (taskreq.getIsTrackerUsed() != null) {
				dbtaskObj.setIsTrackerUsed(taskreq.getIsTrackerUsed());
			}
			if (updateTaskReq.getIsEodUpdate() != null && updateTaskReq.getIsEodUpdate()) {
				dbtaskObj.setIsEodUpdate(Boolean.TRUE);
			}
			dbtaskObj.setUpdatedOn(LocalDateTime.now());
			dbTaskList.add(dbtaskObj);
		}
		this.taskRepository.saveAll(dbTaskList);
		if (updateTaskReq.getIsEodUpdate() != null && updateTaskReq.getIsEodUpdate()) {
			List<Task> taskList = this.taskRepository.findByUserUserIdAndCreatedOn(this.authFacade.getCurrentUserId());
			try {
				sendDailyTaskUpdateMail(taskList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				submitEasyCollabTimesheetData(taskList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public GetTaskResponse addUpdateTask(AddUpdateTaskRequest req) {
		boolean isEodUpdate = req.getIsEodUpdate() == null ? Boolean.FALSE : req.getIsEodUpdate();
		List<Task> dbTaskList = this.addUpdateTask(req.getTaskList(), isEodUpdate);
		if (isEodUpdate) {
			try {
				sendDailyTaskUpdateMail(dbTaskList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				submitEasyCollabTimesheetData(dbTaskList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new GetTaskResponse(convertToTaskDto(dbTaskList));
	}

	private List<Task> addUpdateTask(List<TaskDTO> taskDTOs, boolean isEodupdate) {
		Map<Integer, List<TaskDTO>> projectWiseTask = taskDTOs.stream()
				.collect(Collectors.groupingBy(TaskDTO::getProjectId));
		List<Task> dbTaskList = new ArrayList<>(taskDTOs.size());
		User user = this.userRepository.getOne(this.authFacade.getCurrentUserId());
		for (Map.Entry<Integer, List<TaskDTO>> entry : projectWiseTask.entrySet()) {
			boolean isTodaysTaskExist = this.taskRepository.existsByProjectIdAndUserIdAndCurrentDate(entry.getKey(),
					this.authFacade.getCurrentUserId());
			Project project = this.projectRepository.getOne(entry.getKey());
			for (TaskDTO taskreq : entry.getValue()) {
				boolean isNewTask = taskreq.getTaskId() == null || taskreq.getTaskId().intValue() == 0;
				Task task = null;
				if (isNewTask) {
					task = new Task();
					task.setIsPlanned(!isTodaysTaskExist);
					task.setIsTrackerUsed(Boolean.FALSE);
					task.setIsEodUpdate(Boolean.FALSE);
					task.setIsSubmittedToEasyCollab(Boolean.FALSE);
					task.setUser(user);
					task.setCreatedOn(LocalDateTime.now());
				} else {
					task = this.taskRepository.getOne(taskreq.getTaskId());
					task.setUpdatedOn(LocalDateTime.now());
				}
				if (task.getIsEodUpdate() != null && task.getIsEodUpdate()) {
					throw new BusinessException("EOD status already updated. Cann't update again.");
				}
				task.setTask_desc(taskreq.getTaskDesc());
				task.setEstHr(taskreq.getEstHr());
				if (isNewTask || (!isNewTask && task.getProject().getId() != taskreq.getProjectId())) {
					task.setProject(project);
				}
				if (isEodupdate) {
					task.setActualHr(taskreq.getActualHr());
					task.setStatus(taskreq.getStatus());
					task.setIsTrackerUsed(taskreq.getIsTrackerUsed());
					task.setIsEodUpdate(Boolean.TRUE);
				}
				dbTaskList.add(task);
			}
		}
		return this.taskRepository.saveAll(dbTaskList);
	}

	public void submitEasyCollabTimesheetData() {
		/*
		 * TODO: Query only those task with isEodUpdate=true,
		 * isSubmittedToEasyCollab=false
		 * 
		 */
		List<Task> taskList = this.taskRepository.findByUserUserIdAndCreatedOn(this.authFacade.getCurrentUserId());
		try {
			submitEasyCollabTimesheetData(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void submitEasyCollabTimesheetData(List<Task> taskList) {
		if (!taskList.isEmpty()) {
			for (Task task : taskList) {
				if (task.getIsEodUpdate() && !task.getIsSubmittedToEasyCollab() && (task.getStatus() != null
						&& task.getStatus().intValue() == TaskStatus.COMPLETED.getStatusCode())) {
					String easyCollabProjId = task.getProject().getEasyCollabProjectId();
					String easyCollabTaskId = task.getProject().getEasyCollabTaskId();
					if (easyCollabProjId != null && !easyCollabProjId.trim().isEmpty() && easyCollabTaskId != null
							&& !easyCollabTaskId.trim().isEmpty()) {
						EasyCollabTimeSheet timeSheetFillRequest = new EasyCollabTimeSheet();
						timeSheetFillRequest.setSystemTaskId(task.getId());
						timeSheetFillRequest.setToken(this.easyCollabRESTService.getTimeSheetSubmitUrlToken());
						timeSheetFillRequest.setUser_id(String.valueOf(this.authFacade.getEasyCollabUserId()));
						timeSheetFillRequest.setProject_id(task.getProject().getEasyCollabProjectId());
						timeSheetFillRequest.setTask_id(task.getProject().getEasyCollabTaskId());
						timeSheetFillRequest.setNotes(task.getTask_desc());
						timeSheetFillRequest.setHours(task.getActualHr().toString());
						timeSheetFillRequest
								.setTs_date(DateTimeFormatter.ofPattern("dd-MM-yyyy").format(task.getCreatedOn()));
						try {
							if (this.easyCollabRESTService.timeSheetSubmitAPI(timeSheetFillRequest)) {
								task.setIsSubmittedToEasyCollab(Boolean.TRUE);
								this.taskRepository.save(task);
							}
						} catch (Exception ex) {
							logger.error("Exception while submitting taskId:{} \n {}", task.getId(), ex);
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	public void sendDailyTaskUpdateMail() throws Exception {
		List<Task> taskList = this.taskRepository.findByUserUserIdAndCreatedOn(this.authFacade.getCurrentUserId());
		try {
			sendDailyTaskUpdateMail(taskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendDailyTaskUpdateMail(List<Task> taskList) throws Exception {
		String userEmail = this.authFacade.getUserDetails().getUsername();
		String userFullName = this.authFacade.getUserDetails().getUserFullName();
		Map<Project, List<Task>> projectWiseTask = new HashMap<>();
		if (!taskList.isEmpty()) {
			// TODO: Replace for loop with Stream API toMap
			for (Task task : taskList) {
				if (projectWiseTask.containsKey(task.getProject())) {
					projectWiseTask.get(task.getProject()).add(task);
				} else {
					List<Task> ptaskList = new ArrayList<>();
					ptaskList.add(task);
					projectWiseTask.put(task.getProject(), ptaskList);
				}
			}
			for (Map.Entry<Project, List<Task>> entry : projectWiseTask.entrySet()) {
				List<String> listOfCompletedTask = getTaskListDescByStatus(entry.getValue(), TaskStatus.COMPLETED);
				List<String> listOfInprogressTask = getTaskListDescByStatus(entry.getValue(), TaskStatus.INPROGRESS);
				List<String> listOfRemainingTask = getTaskListDescByStatus(entry.getValue(), TaskStatus.REMAINING);

				File file = new File(
						"E:\\Prashant\\task-multiple-module\\taskservice\\src\\main\\resources\\html_report.html");
//				File file = this.resourceLoader.getResource("classpath:html_report.html").getFile();
//				InputStream inputStream = this.getClass().getResourceAsStream("classpath:html_report.html");
				DailyTaskStatusEmailTemplate emailTemplate = new DailyTaskStatusEmailTemplate(file, null);
				// @formatter:off
				String emailBody = emailTemplate
									.setProjectName(entry.getKey().getProjectName())
									.setListOfCompletedTask(listOfCompletedTask)
									.setListOfInPorgressTask(listOfInprogressTask)
									.setListOfRemainingTask(listOfRemainingTask)
									.generateHtml();
				String subject = "Testing-Updates for {projectName} as on {date}";
				subject = subject.replace("{projectName}", entry.getKey().getProjectName())
					             .replace("{date}",DailyTaskStatusEmailTemplate.dateFormatter.format(LocalDate.now()));
				// @formatter:on
				this.emailService.send(emailBody, subject, userEmail, userFullName);
			}
		}
	}

	private List<String> getTaskListDescByStatus(List<Task> listOfTaks, TaskStatus status) {
		List<String> listOfTaskDesc = new ArrayList<>();
		for (Task task : listOfTaks) {
			if (task.getStatus().intValue() == status.getStatusCode()
					&& task.getStatus().intValue() == TaskStatus.COMPLETED.getStatusCode()) {
				listOfTaskDesc.add(task.getTask_desc() + " - " + task.getActualHr() + " Hr");
			} else if (task.getStatus().intValue() == status.getStatusCode()) {
				listOfTaskDesc.add(task.getTask_desc());
			}
		}
		return listOfTaskDesc;
	}

	@Override
	public GetTaskResponse getAllTask() {
		List<Task> taskList = this.taskRepository.findByUserUserIdAndCreatedOn(this.authFacade.getCurrentUserId());
		return new GetTaskResponse(convertToTaskDto(taskList));
	}

	private List<TaskDTO> convertToTaskDto(List<Task> taskList) {
		if (taskList != null) {
			List<TaskDTO> taskDTOs = new ArrayList<>(taskList.size());
			for (Task taskEntity : taskList) {
				TaskDTO taskDTO = new TaskDTO();
				taskDTO.setProjectId(taskEntity.getProject().getId());
				taskDTO.setTaskId(taskEntity.getId());
				taskDTO.setTaskDesc(taskEntity.getTask_desc());
				taskDTO.setEstHr(taskEntity.getEstHr());
				taskDTO.setActualHr(taskEntity.getActualHr());
				taskDTO.setIsPlanned(taskEntity.getIsPlanned());
				taskDTO.setIsTrackerUsed(taskEntity.getIsTrackerUsed());
				taskDTO.setIsEodUpdate(taskEntity.getIsEodUpdate());
				taskDTO.setStatus(taskEntity.getStatus());
				taskDTO.setCreatedOn(taskEntity.getCreatedOn());
				taskDTO.setUpdatedOn(taskEntity.getUpdatedOn());
				taskDTOs.add(taskDTO);
			}
			return taskDTOs;
		}
		return Collections.emptyList();
	}

	@Override
	public void deleteTask() {
		this.taskRepository.deleteByUser_UserId(this.authFacade.getCurrentUserId());
	}
}