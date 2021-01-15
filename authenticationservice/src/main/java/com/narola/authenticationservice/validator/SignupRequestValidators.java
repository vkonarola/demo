package com.narola.authenticationservice.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narola.authenticationservice.payload.request.SignupRequest;

@Component("signup_request_validator")
public class SignupRequestValidators implements Validator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return SignupRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		this.logger.debug("SignupRequest:{}", target);
		SignupRequest signupRequest = (SignupRequest) target;
		if (!StringUtils.hasText(signupRequest.getEmail())) {
			errors.rejectValue("email", "signup.email.emptpy");
		}
		if (!this.emailValidator.validateEmail(signupRequest.getEmail())) {
			errors.rejectValue("email", "signup.email.invalid");
		}
		if (!StringUtils.hasText(signupRequest.getPassword())) {
			errors.rejectValue("password", "signup.password.empty");
		}
		if (!StringUtils.hasText(signupRequest.getUserFullName())) {
			errors.rejectValue("userFullName", "signup.fullname.empty");
		}
	}
}