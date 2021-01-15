package com.narola.core.task.easycollab.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EasyCollabTimeSheet {

	@JsonIgnore
	private int systemTaskId;

	private String user_id;
	private String notes;
	private String hours;
	private String project_id;
	private String task_id;
	private String ts_date;
	private String token;

	public int getSystemTaskId() {
		return systemTaskId;
	}

	public void setSystemTaskId(int systemTaskId) {
		this.systemTaskId = systemTaskId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getTs_date() {
		return ts_date;
	}

	public void setTs_date(String ts_date) {
		this.ts_date = ts_date;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	@Override
	public String toString() {
		return "EasyCollabTimeSheet [token=" + token + ", user_id=" + user_id + ", notes=" + notes + ", hours=" + hours
				+ ", ts_date=" + ts_date + ", project_id=" + project_id + ", task_id=" + task_id + "]";
	}
}
