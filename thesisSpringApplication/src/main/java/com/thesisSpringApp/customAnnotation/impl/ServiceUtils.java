package com.thesisSpringApp.customAnnotation.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thesisSpringApp.service.UserService;

@Component
public class ServiceUtils {
	private static ServiceUtils instance;

	@Autowired
	private UserService userService;

	/* Post constructor */

	@PostConstruct
	public void fillInstance() {
		instance = this;
	}

	/* static methods */

	public static UserService getUserService() {
		return instance.userService;
	}
}