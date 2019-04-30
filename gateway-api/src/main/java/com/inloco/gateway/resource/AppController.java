package com.inloco.gateway.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inloco.gateway.exception.ApiError;
import com.inloco.gateway.exception.ErrorsMessage;

@RestController
public class AppController implements ErrorController {

	private static final String PATH = "/error";
	
	@RequestMapping(value = PATH)
    @ResponseBody
    public ResponseEntity<Object> error(HttpServletRequest request, HttpServletResponse response) {
		ApiError apiError = new ApiError(ErrorsMessage.INTERNAL_ERROR.getErrorMessage(),ErrorsMessage.INTERNAL_ERROR.getErrorCode());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.valueOf(response.getStatus()));
    }
	
	@Override
	public String getErrorPath() {
		return PATH;
	}

}
