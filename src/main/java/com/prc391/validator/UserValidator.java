package com.prc391.validator;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.prc391.models.User;
import com.prc391.repositories.UserRepository;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        
        if (!user.getUsername().matches("^(.+)@(.+)$")) {
            errors.rejectValue("username", "Format.userForm.username");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullname", "NotEmpty");
        if (user.getFullname().length() < 6 || user.getFullname().length() > 64) {
            errors.rejectValue("fullname", "Size.userForm.fullname");
        }
        
        if (userRepo.findExisted(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userForm.matchingPassword");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthdate", "NotEmpty");
        try {
        	if((new Date(System.currentTimeMillis()).getTime() - user.getBirthdate().getTime()) < 189388800000L) {
            	errors.rejectValue("birthdate", "Diff.userForm.birthdate");
            } else if((new Date(System.currentTimeMillis()).getTime() - user.getBirthdate().getTime()) > 3160000000000L) {
            	errors.rejectValue("birthdate", "Invalid.userForm.birthdate");
            }
		} catch (Exception e) {
			errors.rejectValue("birthdate", "Invalid.userForm.birthdate");
		}
	}
}
