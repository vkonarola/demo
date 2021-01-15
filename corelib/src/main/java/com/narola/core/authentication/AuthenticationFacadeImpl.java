package com.narola.core.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements IAuthenticationFacade {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public CustomUserDetail getUserDetails() {
		return (CustomUserDetail) getAuthentication().getPrincipal();
	}

	@Override
	public int getCurrentUserId() {
		return getUserDetails().getUserId();
	}

	@Override
	public int getEasyCollabUserId() {
		return getUserDetails().getEasyCollabUserId();
	}

}
