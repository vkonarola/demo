package com.narola.authenticationservice.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.narola.authenticationservice.payload.request.SignupRequest;
import com.narola.authenticationservice.service.IUserService;
import com.narola.core.authentication.entity.User;
import com.narola.core.authentication.repository.RoleRepository;
import com.narola.core.authentication.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean existByEmailId(String emailId) {
		return this.userRepository.existsByEmailId(emailId);
	}

	@Override
	public void signUpUser(SignupRequest signupRequest) {
		User user = new User();
		user.setEmailId(signupRequest.getEmail());
		user.setName("Dummy"); // For Test Only, later on field value will be fetch from request
		user.setIsThirdPartyUser(Boolean.FALSE);
		user.setRole(this.roleRepository.getOne(1)); // For Test Only. later on admin will assign role after signup
		user.setPassword(this.passwordEncoder.encode(signupRequest.getPassword()));
		user.setCreatedOn(LocalDateTime.now());
		this.userRepository.save(user);
	}

	@Override
	public User signUpEasyCollabUser(SignupRequest signupRequest) {
		User user = new User();
		user.setEmailId(signupRequest.getEmail());
		user.setName(signupRequest.getUserFullName());
		user.setEasyCollabUserId(signupRequest.getEasyCollabUserId());
		user.setIsThirdPartyUser(Boolean.TRUE);
		user.setRole(this.roleRepository.getOne(1)); // For Test Only. later on admin will assign role after signup
		user.setCreatedOn(LocalDateTime.now());
		return this.userRepository.save(user);
	}

	@Override
	public Optional<User> getUserByEmailId(String emailId) {
		return this.userRepository.findByEmailId(emailId.trim());
	}

}