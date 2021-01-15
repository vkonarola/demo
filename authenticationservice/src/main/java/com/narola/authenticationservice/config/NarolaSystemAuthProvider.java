package com.narola.authenticationservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;

import com.narola.authenticationservice.payload.request.SignupRequest;
import com.narola.authenticationservice.service.IUserService;
import com.narola.core.authentication.CustomUserDetail;
import com.narola.core.authentication.entity.User;

public class NarolaSystemAuthProvider implements AuthenticationProvider {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String API_RESPONSE_PARAM_MESSAGE = "Message";
	private static final String API_RESPONSE_PARAM_USERNAME = "User_name";
	private static final String API_RESPONSE_PARAM_USERID = "user_id";
	private static final String API_RESPONSE_PARAM_EMAIL = "Email";
	private static final String API_INVALID_CREDENTIAL_MSG = "Invalid Email-Password. Please contact to admin.";
	private static final String API_SUCESSFULL_MSG = "Successfully Login and Device Token updated.";

	private String loginUrl;

	@Autowired
	private IUserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		Map<String, String> response = authentiateToNarola(userName, password);
		String message = response.get(API_RESPONSE_PARAM_MESSAGE);
		if (!message.equals(API_SUCESSFULL_MSG)) {
			throw new BadCredentialsException(API_INVALID_CREDENTIAL_MSG);
		}
		Optional<User> opUser = this.userService.getUserByEmailId(userName);
		String userFullName = (String) response.get(API_RESPONSE_PARAM_USERNAME);
		int easyCollabUserId = Integer.valueOf((String) response.get(API_RESPONSE_PARAM_USERID));
		User dbUser = null;
		if (!opUser.isPresent()) {
			SignupRequest signupRequest = new SignupRequest();
			signupRequest.setEmail((String) response.get(API_RESPONSE_PARAM_EMAIL));
			signupRequest.setUserFullName(userFullName);
			signupRequest.setEasyCollabUserId(easyCollabUserId);
			dbUser = this.userService.signUpEasyCollabUser(signupRequest);
		} else {
			dbUser = opUser.get();
		}
		// @formatter:off
		CustomUserDetail customUser = new CustomUserDetail(dbUser.getUserId(), 
														   easyCollabUserId, 
														   userName, 
														   password,
														   userFullName, getAuthority());
		// @formatter:on
		return new UsernamePasswordAuthenticationToken(customUser, null, getAuthority());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> authentiateToNarola(String name, String password) {
		String targetUrl = this.loginUrl.replace("##email##", name).replace("##password##", password);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.postForEntity(targetUrl, null, Map.class);
		this.logger.debug("authentiateToNarola:{}", response.getBody());
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new BadCredentialsException("Oops something went wrong. Please contact to admin.");
		}
		return response.getBody();
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_DEV"));
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
