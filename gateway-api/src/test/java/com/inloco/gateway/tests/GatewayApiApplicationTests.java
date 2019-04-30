package com.inloco.gateway.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inloco.gateway.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApiApplicationTests {
	
	@Autowired
	private UserService userService;

	@Test
	public void checkUserRegistered() {
//		User newUser = new User();
//		newUser.setFirstName("Eduardo");
//		newUser.setLastName("Lima");
//		newUser.setPassword(new BCryptPasswordEncoder().encode("123456"));
//		newUser.setEmail("eduardostudio@gmail.com");
//		
//		
//		if (userService.findByUserEmail("eduardostudio@gmail.com") == null) {
//			userService.saveOrUpdate(newUser);
//		}
//		
//		User userFinded = userService.findByUserEmail("eduardostudio@gmail.com");
//		
//		assertEquals(newUser.getEmail(), userFinded.getEmail());
	}

	@Test
	public void verifyUserPassword() {
		
//		String expectedPassword = "123456";
//		
//		User user = userService.findByUserEmail("eduardostudio@gmail.com");
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		
//		boolean matches = bCryptPasswordEncoder.matches(expectedPassword, user.getPassword());
//		
//		assertSame(true, matches);
	}
}
