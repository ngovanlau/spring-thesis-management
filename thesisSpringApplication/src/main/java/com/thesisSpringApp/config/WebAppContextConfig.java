/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.thesisSpringApp.formatters.FacultyFormatter;
import com.thesisSpringApp.formatters.RoleFormatter;

/**
 *
 * @author ADMIN
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({
		"com.thesisSpringApp.controller",
		"com.thesisSpringApp.repository",
		"com.thesisSpringApp.service",
		"com.thesisSpringApp.api",
		"com.thesisSpringApp.config",
		"com.thesisSpringApp.customAnnotation"
})
public class WebAppContextConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver i = new InternalResourceViewResolver();
		i.setViewClass(JstlView.class);
		i.setPrefix("/WEB-INF/pages/");
		i.setSuffix(".jsp");

		return i;
	}

	@Override
	public void addResourceHandlers(
			ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**")
				.addResourceLocations("/resources/css/");
		registry.addResourceHandler("/img/**")
				.addResourceLocations("/resources/images/");
		registry.addResourceHandler("/fonts/**")
				.addResourceLocations("/resources/fonts/");
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");

		return resolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Áp dụng CORS cho tất cả các URL
				.allowedOrigins("*") // Cho phép truy cập từ tất cả các domain
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Cho phép các phương thức HTTP
				.allowedHeaders("*"); // Cho phép tất cả các header
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages"); // đọc tập tin có tên là messages.properties
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@Override
	public Validator getValidator() {
		return validator();
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new RoleFormatter());
		registry.addFormatter(new FacultyFormatter());
	}
}