package com.narola.taskservice.payload.response;

import java.util.List;

import com.narola.taskservice.payload.dto.TaskDTO;

public class GetTaskResponse {

	private List<TaskDTO> taskList;

	public GetTaskResponse() {
		// TODO Auto-generated constructor stub
	}

	public GetTaskResponse(List<TaskDTO> taskList) {
		this.taskList = taskList;
	}

	public List<TaskDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskDTO> taskList) {
		this.taskList = taskList;
	}

}
