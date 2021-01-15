package com.narola.authenticationservice.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

	private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private static Pattern pattern;

	public EmailValidator() {
		pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	public boolean validateEmail(String email) {
		return pattern.matcher(email).matches();
	}
}
