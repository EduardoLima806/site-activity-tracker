package com.inloco.gateway.security;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.inloco.gateway.model.User;
import com.inloco.gateway.service.UserService;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String pw = authentication.getCredentials().toString();
		
		User loginUser = userService.loginUser(username, pw);
		
		if (loginUser != null) {
			loginUser.setLastLogin(new Date());
			userService.saveOrUpdate(loginUser);
			return 
				new UsernamePasswordAuthenticationToken(username, pw, Collections.emptyList());
		} else {
			throw new BadCredentialsException("Invalid e-mail or password");
		}
	}

}
