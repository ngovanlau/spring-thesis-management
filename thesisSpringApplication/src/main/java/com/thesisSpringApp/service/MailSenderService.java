package com.thesisSpringApp.service;

import javax.mail.MessagingException;

import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.User;

public interface MailSenderService{

	void sendEmail(String from, User user) throws MessagingException;
	void sendOtp(String otp, User user) throws MessagingException;

	void sendEmailForLecture(String from, User user, Committee committee) throws MessagingException;

	void sendEmailForPupils(String from, User user, Thesis thesis) throws MessagingException;

}
