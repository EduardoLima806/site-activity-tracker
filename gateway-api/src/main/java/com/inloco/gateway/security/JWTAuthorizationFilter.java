package com.inloco.gateway.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inloco.gateway.exception.ApiError;
import com.inloco.gateway.exception.ErrorsMessage;
import com.inloco.gateway.exception.TokenNotSentException;

import io.jsonwebtoken.ExpiredJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		try {

			UsernamePasswordAuthenticationToken authentication;

			authentication = (UsernamePasswordAuthenticationToken) TokenAuthenticationService
					.getAuthentication(request);
			if (authentication == null) {
				throw new TokenNotSentException(null, null, null);
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch (Exception e) {
			handleException(e, response);
		}

	}

	private void handleException(Exception ex, HttpServletResponse response)
			throws JsonProcessingException, IOException {

		ApiError apiError;

		response.setContentType("application/json");
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		if (ex instanceof ExpiredJwtException) {
			apiError = new ApiError(ErrorsMessage.TOKEN_EXPIRED.getErrorMessage(),
					ErrorsMessage.TOKEN_EXPIRED.getErrorCode());
		} else if (ex instanceof TokenNotSentException) {
			apiError = new ApiError(ErrorsMessage.TOKEN_NOT_SENT.getErrorMessage(),
					ErrorsMessage.TOKEN_NOT_SENT.getErrorCode());
		} else {
			apiError = new ApiError(ErrorsMessage.INTERNAL_ERROR.getErrorMessage(),
					ErrorsMessage.INTERNAL_ERROR.getErrorCode());
		}

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