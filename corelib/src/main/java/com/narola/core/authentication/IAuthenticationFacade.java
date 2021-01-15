package com.narola.core.authentication;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
	Authentication getAuthentication();

	CustomUserDetail getUserDetails();

	int getCurrentUserId();
	
	int getEasyCollabUserId();
}
