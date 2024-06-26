package com.thesisSpringApp.customAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.thesisSpringApp.customAnnotation.impl.UniqueValueFiledValidatorImpl;

@Constraint(validatedBy = UniqueValueFiledValidatorImpl.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueValueField {
	String message() default "This value already exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String fieldName() default "";
}
