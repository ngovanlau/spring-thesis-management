/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thesisSpringApp.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author ADMIN
 */
public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				HibernateConfig.class,
				TilesConfig.class,
				BeanConfig.class,
				MailConfig.class,
				SecurityConfig.class,
				CloudinaryConfig.class,
				PaymentVnPayConfig.class,
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
				WebAppContextConfig.class

		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {
				"/"
		};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] { encodingFilter };
	}

}
