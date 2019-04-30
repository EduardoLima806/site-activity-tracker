package com.inloco.gateway.service;

import com.inloco.gateway.model.User;

public interface UserService {

	void saveOrUpdate(User user);
	
	User loginUser(String email, String password);
	
	User findByUserEmail(String email);
}
