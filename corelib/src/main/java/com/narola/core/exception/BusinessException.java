package com.narola.core.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7833776528827682114L;

	public BusinessException(String message) {
		super(message);
	}
}
