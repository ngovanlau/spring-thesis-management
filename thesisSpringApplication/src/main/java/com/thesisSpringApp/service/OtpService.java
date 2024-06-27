package com.thesisSpringApp.service;

import com.thesisSpringApp.pojo.Otp;
import com.thesisSpringApp.pojo.User;

import javax.mail.MessagingException;

public interface OtpService {
    void generateOrUpdateOtp(User user) throws MessagingException;

    void deleteOtp(User user);

    Boolean validateOtp(User user, String otp_code);
}
