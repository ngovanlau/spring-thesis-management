package com.thesisSpringApp.customAnnotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thesisSpringApp.customAnnotation.UniqueValueField;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.UserService;

@Component
public class UniqueValueFiledValidatorImpl
		implements ConstraintValidator<UniqueValueField, String> {

	@Autowired
	public UserService userService;


	private String fieldName;
	private static Integer userId;

	public void setUserId(Integer userId) {
		UniqueValueFiledValidatorImpl.userId = userId;
	}

	@Override
	public void initialize(UniqueValueField constraintAnnotation) {
		this.fieldName = constraintAnnotation.fieldName();
		userService = ServiceUtils.getUserService();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {


		if (value == null || value.isEmpty() || value.equals(""))
			return true;


		boolean isUnique = false;
		switch (fieldName) {
		case "email":
			User existingUser = userService.getUserByEmail(value);
			if (existingUser != null) {
				if (userId != null && existingUser.getId().equals(userId))
					isUnique = true;
			} else
				isUnique = !userService.isUserExistsByEmail(value);
			break;
		case "phone":
			User existingUser2 = userService.getUserByPhone(value);
			if (existingUser2 != null) {
				if (userId != null && existingUser2.getId().equals(userId))
					isUnique = true;
			} else
				isUnique = !userService.isUserExistsByPhone(value);
			break;
		case "useruniversityid":
			User existingUser3 = userService.getUserByUniversityId(value);
			if (existingUser3 != null) {
				if (userId != null && existingUser3.getId().equals(userId))
					isUnique = true;
			} else
				isUnique = !userService.isUserExistsByUniversityId(value);
			break;
		default:
			throw new IllegalArgumentException("Unknown field name: " + fieldName);
		}

		return isUnique;
	}
}
