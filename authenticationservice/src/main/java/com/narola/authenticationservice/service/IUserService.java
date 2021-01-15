package com.narola.authenticationservice.service;

import java.util.Optional;

import com.narola.authenticationservice.payload.request.SignupRequest;
import com.narola.core.authentication.entity.User;

public interface IUserService {

	boolean existByEmailId(String emailId);

	Optional<User> getUserByEmailId(String emailId);

	void signUpUser(SignupRequest signupRequest);

	User signUpEasyCollabUser(SignupRequest signupRequest);

}
