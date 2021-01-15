package com.narola.core.payload.response;

public class APIResponse {

	private String errorMessage;
	private Object data;

	public String getErrorMessage() {
		return errorMessage;
	}

	public APIResponse setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}

	public Object getData() {
		return data;
	}

	public APIResponse setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "APIResponse [errorMessage=" + errorMessage + ", data=" + data + "]";
	}
}
