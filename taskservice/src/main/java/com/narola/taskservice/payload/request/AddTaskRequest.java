package com.narola.taskservice.payload.request;

import java.util.List;

import com.narola.taskservice.payload.dto.TaskDTO;

public class AddTaskRequest {

	private List<TaskDTO> taskList;

	public List<TaskDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskDTO> taskList) {
		this.taskList = taskList;
	}

	@Override
	public String toString() {
		return "AddTaskRequest [taskList=" + taskList + "]";
	}

}
