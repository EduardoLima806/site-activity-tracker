package com.inloco.gateway.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.inloco.gateway.model.User;
import com.inloco.gateway.repository.UserRepository;

public class UserEmailValidator implements ConstraintValidator<EmailExistsContraint, String> {

	@Autowired
    private UserRepository userRepository;
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		boolean isValid = true;
		
		if (userRepository != null) { // javax.validation.ValidationException: HV000028: Unexpected exception during isValid call. (https://github.com/paypal/paypal-checkout-components/issues/881)
			List<User> users = userRepository.findByEmail(email);
			
			if (users != null && !users.isEmpty()) {
				isValid = false;
			}
		}
		
		
		return isValid;
	}

	
}
