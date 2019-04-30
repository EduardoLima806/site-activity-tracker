package com.inloco.gateway.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inloco.gateway.exception.ApiError;
import com.inloco.gateway.exception.ErrorsMessage;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	

	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		Authentication auth = null;
		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(),
				AccountCredentials.class);
		
		if (StringUtils.isEmpty(credentials.getEmail()) || StringUtils.isEmpty(credentials.getPassword())) {
			
			ApiError apiError = new ApiError(ErrorsMessage.MISSING_FIELDS.getErrorMessage(), ErrorsMessage.MISSING_FIELDS.getErrorCode());
			writeError(apiError, response);
			
		} else {
			
			auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), credentials.getPassword(), Collections.emptyList()));
		}

		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		ApiError apiError = new ApiError(ErrorsMessage.INVALID_EMAIL_PASSWORD.getErrorMessage(), ErrorsMessage.INVALID_EMAIL_PASSWORD.getErrorCode());
		writeError(apiError, response);
	}
	
	private void writeError(ApiError apiError, HttpServletResponse response) throws JsonProcessingException, IOException {
		
		response.setContentType("application/json");
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.getWriter().write(convertObjectToJson(apiError));
	}
	
	public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
	
}