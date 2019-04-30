package com.inloco.gateway.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public enum ErrorsMessage {

	MISSING_FIELDS(120, "error.api.120"),
	MISMATCH_TYPE(121, "error.api.122"),
	INVALID_FIELDS(122, "error.api.122"),
	CONSTRAINT_FIELDS(123, "error.api.123"),
	TOKEN_NOT_SENT(124, "error.api.124"),
	TOKEN_EXPIRED(125, "error.api.125"),
	INTERNAL_ERROR(126, "error.api.126"),
	EMAIL_ALREADY_EXISTS(127, "error.api.127"),
	INVALID_EMAIL_PASSWORD(128, "error.api.128");
	
	private Integer errorCode;
	private String errorMessage;
	
	private ErrorsMessage(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return 
			messageSource().getMessage(errorMessage, null, Locale.ENGLISH);
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	     
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
}
