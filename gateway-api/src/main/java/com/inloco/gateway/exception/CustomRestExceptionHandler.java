package com.inloco.gateway.exception;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ApiError apiError = null;
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			if (Arrays.asList(error.getCodes()).contains("EmailExistsContraint")) {
				apiError = new ApiError(ErrorsMessage.EMAIL_ALREADY_EXISTS.getErrorMessage(), ErrorsMessage.EMAIL_ALREADY_EXISTS.getErrorCode());
			}
			if (Arrays.asList(error.getCodes()).contains("NotBlank")) {
				apiError = new ApiError(ErrorsMessage.MISSING_FIELDS.getErrorMessage(), ErrorsMessage.MISSING_FIELDS.getErrorCode());
			}
        }
		
		if (apiError == null) {
			apiError = new ApiError(ErrorsMessage.INVALID_FIELDS.getErrorMessage(), ErrorsMessage.INVALID_FIELDS.getErrorCode());
		}
		
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(ErrorsMessage.MISSING_FIELDS.getErrorMessage(), ErrorsMessage.MISSING_FIELDS.getErrorCode());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

		ApiError apiError = new ApiError(ErrorsMessage.CONSTRAINT_FIELDS.getErrorMessage(), ErrorsMessage.CONSTRAINT_FIELDS.getErrorCode());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		ApiError apiError = new ApiError(ErrorsMessage.MISMATCH_TYPE.getErrorMessage(), ErrorsMessage.MISMATCH_TYPE.getErrorCode());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
}
