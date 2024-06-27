package com.thesisSpringApp.service.serviceimpl;

import com.thesisSpringApp.pojo.Otp;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.repository.OtpRepository;
import com.thesisSpringApp.service.MailSenderService;
import com.thesisSpringApp.service.OtpService;
import com.thesisSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public void generateOrUpdateOtp(User user) throws MessagingException {
        String otp_code = userService.generateRandomString(6);
        Otp otp = otpRepository.getOtpByUser(user);

        if (otp == null)
           otp = new Otp(user);

        otp.setOtpCode(passwordEncoder.encode(otp_code));
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(2));

        otpRepository.saveOrUpdateOtp(otp);

        mailSenderService.sendOtp(otp_code, user);
    }

    @Override
    public void deleteOtp(User user) {
        Otp otp = otpRepository.getOtpByUser(user);
        if (otp != null) {
            otpRepository.deleteOtp(otp);
        }
    }

    @Override
    public Boolean validateOtp(User user, String otp_code) {
        Otp otp = otpRepository.getOtpByUser(user);

        if (otp == null)
            return false;

        if (otp.getExpiryTime().isAfter(LocalDateTime.now())) {
            return passwordEncoder.matches(otp_code, otp.getOtpCode());
        }

        return false;
    }
}
