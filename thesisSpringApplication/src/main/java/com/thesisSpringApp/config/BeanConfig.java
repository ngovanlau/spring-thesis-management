package com.thesisSpringApp.config;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.thesisSpringApp.pojo.User;


@Configuration
public class BeanConfig {

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public User user() {
		return new User();
	}


}
