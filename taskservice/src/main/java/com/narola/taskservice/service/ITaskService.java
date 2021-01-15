package com.narola.taskservice.service;

import java.util.List;

import com.narola.core.task.entity.Task;
import com.narola.taskservice.payload.request.AddTaskRequest;
import com.narola.taskservice.payload.request.AddUpdateTaskRequest;
import com.narola.taskservice.payload.request.UpdateTaskRequest;
import com.narola.taskservice.payload.response.GetTaskResponse;

public interface ITaskService {

	GetTaskResponse addTask(AddTaskRequest addTaskRequest);

	GetTaskResponse addUpdateTask(AddUpdateTaskRequest addUpdateTaskRequest);

	void updateTask(UpdateTaskRequest updateTaskRequest) throws Exception;

	GetTaskResponse getAllTask();

	void sendDailyTaskUpdateMail(List<Task> taskList) throws Exception;

	void sendDailyTaskUpdateMail() throws Exception;

	void submitEasyCollabTimesheetData();

	void submitEasyCollabTimesheetData(List<Task> taskList);

	void deleteTask();

}
