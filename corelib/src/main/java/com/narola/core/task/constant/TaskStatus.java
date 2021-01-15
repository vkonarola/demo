package com.narola.core.task.constant;

public enum TaskStatus {

	REMAINING(3), INPROGRESS(2), COMPLETED(1);

	private final int statusCode;

	TaskStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static boolean isValidStatusCode(int statusCode) {
		if (COMPLETED.getStatusCode() != statusCode && INPROGRESS.getStatusCode() != statusCode
				&& REMAINING.getStatusCode() != statusCode) {
			return false;
		}
		return true;
	}
}
