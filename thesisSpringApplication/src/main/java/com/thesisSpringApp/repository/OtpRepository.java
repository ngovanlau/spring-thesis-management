package com.thesisSpringApp.repository;

import com.thesisSpringApp.pojo.Otp;
import com.thesisSpringApp.pojo.User;

public interface OtpRepository {
    void saveOrUpdateOtp(Otp otp);

    void deleteOtp(Otp otp);

    Otp getOtpByUser(User user);
}
