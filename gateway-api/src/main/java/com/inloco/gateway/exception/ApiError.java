package com.inloco.gateway.exception;

public class ApiError {
	 
    private String message;
    private Integer errorCode;
 
    public ApiError(String message, Integer errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}