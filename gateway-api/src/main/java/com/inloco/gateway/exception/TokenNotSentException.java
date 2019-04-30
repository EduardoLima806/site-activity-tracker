package com.inloco.gateway.exception;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class TokenNotSentException extends ClaimJwtException {

	public TokenNotSentException(Header header, Claims claims, String message) {
		super(header, claims, message);
	}
}
