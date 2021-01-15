package com.narola.authenticationservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.authenticationservice.payload.request.LoginRequest;

@Component("login_request_validator")
public class LoginRequestValidator implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("LoginRequest:{}", target);
		LoginRequest loginRequest = (LoginRequest) target;
		if (!StringUtils.hasText(loginRequest.getUsername())) {
			errors.rejectValue("username", "login.credentials.invalid");
		}
		if (!this.emailValidator.validateEmail(loginRequest.getUsername())) {
			errors.rejectValue("username", "login.credentials.invalid");
		}
		if (!StringUtils.hasText(loginRequest.getPassword())) {
			errors.rejectValue("password", "login.credentials.invalid");
		}
	}
}