package com.thesisSpringApp.config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thesisSpringApp.filter.CustomAccessDeniedHandler;
import com.thesisSpringApp.filter.JwtAuthenticationTokenFilter;
import com.thesisSpringApp.filter.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.thesisSpringApp.controller",
		"com.thesisSpringApp.repository",
		"com.thesisSpringApp.service",
		"com.thesisSpringApp.config",
		"com.thesisSpringApp.JwtComponents",
})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().ignoringAntMatchers("/api/**");
		http.authorizeRequests().antMatchers("/api/users/login/").permitAll();
		http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers(HttpMethod.PATCH, "/api/theses/committee/")
				.access("hasRole('ROLE_GIAOVU')")
				.antMatchers(HttpMethod.POST, "/api/theses/scores/")
				.access("hasRole('ROLE_GIANGVIEN')")
				.antMatchers(HttpMethod.POST, "/api/committees/")
				.access("hasRole('ROLE_GIAOVU')")
				.antMatchers(HttpMethod.PATCH, "/api/committees/{committeeId}/close/")
				.access("hasRole('ROLE_GIAOVU')")
				.antMatchers(HttpMethod.POST, "/api/criteria/")
				.access("hasRole('ROLE_GIAOVU')")
				.antMatchers(HttpMethod.GET, "/api/users/committees/")
				.access("hasRole('ROLE_GIANGVIEN')")
				.antMatchers(HttpMethod.POST, "/api/users/init-account/")
				.access("hasRole('ROLE_GIANGVIEN') or hasRole('ROLE_SINHVIEN') or hasRole('ROLE_GIAOVU')")
				.antMatchers(HttpMethod.GET, "/api/users/theses/")
				.access("hasRole('ROLE_SINHVIEN')")
				.antMatchers(HttpMethod.POST, "/api/payment/")
				.access("hasRole('ROLE_SINHVIEN')")
				.antMatchers(HttpMethod.POST, "/api/payment/check-payment/")
				.access("hasRole('ROLE_SINHVIEN')")
				.antMatchers(HttpMethod.POST, "/api/pdf/generate/")
				.access("hasRole('ROLE_SINHVIEN')")
				.antMatchers(HttpMethod.GET, "/api/users/lecturer/")
				.access("hasRole('ROLE_GIANGVIEN')")
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter(),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
