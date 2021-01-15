package com.narola.authenticationservice.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.narola.authenticationservice.config.jwt.JwtUtils;
import com.narola.authenticationservice.payload.request.LoginRequest;
import com.narola.authenticationservice.payload.request.SignupRequest;
import com.narola.authenticationservice.payload.response.JwtResponse;
import com.narola.authenticationservice.service.IUserService;
import com.narola.core.authentication.CustomUserDetail;
import com.narola.core.exception.BusinessException;
import com.narola.core.payload.response.APIResponse;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private IUserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("login_request_validator")
	private Validator loginReqValidator;

	@Autowired
	@Qualifier("signup_request_validator")
	private Validator signupReqValidator;

	@PostMapping("/login")
	public ResponseEntity<APIResponse> authenticateUser(@RequestBody LoginRequest loginRequest, BindingResult result) {
		this.loginReqValidator.validate(loginRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getFieldError().getCode(), null, Locale.getDefault()));
		}
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
		return ResponseEntity.ok(new APIResponse()
				.setData(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getUserFullName())));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest, BindingResult result) {
		this.signupReqValidator.validate(signUpRequest, result);
		if (result.hasErrors()) {
			throw new BusinessException(
					this.messageSource.getMessage(result.getFieldError().getCode(), null, Locale.getDefault()));
		}
		if (this.userService.existByEmailId(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest()
					.body(new APIResponse().setErrorMessage("Error: EmailId is already registered"));
		}
		this.userService.signUpUser(signUpRequest);
		return ResponseEntity.ok().build();
	}
}