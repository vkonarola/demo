package com.narola.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.narola.core.payload.response.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = { BusinessException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public APIResponse handleBusinessException(BusinessException ex, HttpServletRequest request) {
		this.logger.debug("GlobalExceptionHandler:{}", ex);
		return new APIResponse().setErrorMessage(ex.getMessage());
	}

	@ExceptionHandler(value = { Exception.class, NullPointerException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public APIResponse handleException(Exception ex, HttpServletRequest request) {
		this.logger.debug("GlobalExceptionHandler:{}", ex);
		return new APIResponse().setErrorMessage(ex.getMessage());
	}

}