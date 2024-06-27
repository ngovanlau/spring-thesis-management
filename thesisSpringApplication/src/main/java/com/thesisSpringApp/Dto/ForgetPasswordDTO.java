package com.thesisSpringApp.Dto;

import lombok.Data;

@Data
public class ForgetPasswordDTO {
    private String username;
    private String password;
    private String otp_code;
}
