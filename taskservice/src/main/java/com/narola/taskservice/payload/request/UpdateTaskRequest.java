package com.narola.taskservice.payload.request;

import java.util.List;

import com.narola.taskservice.payload.dto.TaskDTO;

public class UpdateTaskRequest {

	private List<TaskDTO> taskList;
	private Boolean isEodUpdate;

	public List<TaskDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<TaskDTO> taskList) {
		this.taskList = taskList;
	}

	public Boolean getIsEodUpdate() {
		return isEodUpdate;
	}

	public void setIsEodUpdate(Boolean isEodUpdate) {
		this.isEodUpdate = isEodUpdate;
	}

	@Override
	public String toString() {
		return "UpdateTaskRequest [taskList=" + taskList + ", isEodUpdate=" + isEodUpdate + "]";
	}
}
