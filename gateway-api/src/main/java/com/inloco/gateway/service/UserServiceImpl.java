package com.inloco.gateway.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inloco.gateway.model.User;
import com.inloco.gateway.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public void saveOrUpdate(User user) {
		userRepository.save(user);
	}

	@Override
	public User loginUser(String email, String password) {
		
		BCryptPasswordEncoder passwordEncoder = passwordEncoder();
		User user = this.findByUserEmail(email);
		
		if (user != null) {
			boolean matches = passwordEncoder.matches(password, user.getPassword());
			
			if (!matches) {
				return null;
			}
		}
		
		return user;
	}

	@Override
	public User findByUserEmail(String email) {
		
		User user = null;
		List<User> users = userRepository.findByEmail(email);
		
		if (users != null && !users.isEmpty()) {
			user = users.get(0);
		}
		
		return user;
	}
	
	public BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
	
}
