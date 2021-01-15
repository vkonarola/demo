package com.narola.authenticationservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.narola.core.authentication.CustomUserDetail;
import com.narola.core.authentication.entity.User;
import com.narola.core.authentication.repository.UserRepository;

@Service(value = "custom_userdetail_service")
public class UserDetailServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opUser = this.userRepository.findByEmailId(username);
		if (!opUser.isPresent()) {
			this.logger.debug("EmailId is not registered");
			throw new UsernameNotFoundException("EmailId is not registered.");
		}
		// @formatter:off
		return new CustomUserDetail(opUser.get().getUserId(), 
								    opUser.get().getEasyCollabUserId(), 
								    username,
								    opUser.get().getIsThirdPartyUser() ? "DUMMYPASSWORD" : opUser.get().getPassword(),
								    opUser.get().getName(), getAuthority(opUser.get()));
		// @formatter:on
//		return new CustomUserDetail(opUser.get().getUserId(), username,
//				opUser.get().getIsThirdPartyUser() ? "DUMMYPASSWORD" : opUser.get().getPassword(),
//				getAuthority(opUser.get()));
	}

	private List<SimpleGrantedAuthority> getAuthority(User user) {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
	}

}
