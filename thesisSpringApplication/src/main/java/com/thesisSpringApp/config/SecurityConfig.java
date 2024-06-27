package com.thesisSpringApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.thesisSpringApp.controller",
		"com.thesisSpringApp.repository",
		"com.thesisSpringApp.service",
		"com.thesisSpringApp.api",
		"com.thesisSpringApp.config",

})
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/users/**")
				.access("hasRole('ROLE_SINHVIEN') or hasRole('ROLE_GIANGVIEN') or hasRole('ROLE_GIAOVU')")
				.and().csrf().disable();

		http.formLogin().usernameParameter("username").passwordParameter("password");

		http.formLogin().defaultSuccessUrl("/")
				.failureUrl("/login?error");

		http.logout().permitAll();
		http.logout().logoutSuccessUrl("/login");

		http.exceptionHandling()
				.accessDeniedPage("/login?accessDenied");
	}


}
