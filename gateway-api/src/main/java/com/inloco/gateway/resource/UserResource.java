package com.inloco.gateway.resource;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inloco.gateway.exception.ApiError;
import com.inloco.gateway.exception.ErrorsMessage;
import com.inloco.gateway.model.User;
import com.inloco.gateway.security.SecurityConstants;
import com.inloco.gateway.security.TokenAuthenticationService;
import com.inloco.gateway.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping("/singup")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = passwordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.saveOrUpdate(user);
		Map<Object, Object> model = new HashMap<>();
		model.put("token", TokenAuthenticationService.getJWTToken(user.getEmail()));
		return ResponseEntity.ok().body(model);
	}

	@PostMapping("/singin")
	public ResponseEntity<Object> loginUser(@RequestBody LoginRequest login) {

		return ResponseEntity.ok().body("Authentication success");
	}

	@PostMapping("/me")
	public ResponseEntity<Object> loadUser(@RequestHeader(name = SecurityConstants.HEADER_STRING) String token) throws JsonProcessingException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			
			User user = userService.findByUserEmail(authentication.getName());
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("firstName", user.getFirstName());
			obj.put("lastName", user.getLastName());
			obj.put("email", user.getEmail());
       		obj.put("phones", user.getPhones());
			obj.put("created_at", user.getCreatedAt().toString());
			obj.put("last_login", user.getLastLogin().toString());
			
			return ResponseEntity.ok().body(obj);
		} else {
			
			ApiError apiError = new ApiError(ErrorsMessage.TOKEN_EXPIRED.getErrorMessage(),ErrorsMessage.TOKEN_EXPIRED.getErrorCode());
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	private BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
}