package com.inloco.gateway.resource;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Provide the email")
	private String email;
	@NotBlank(message = "Provide the password")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
