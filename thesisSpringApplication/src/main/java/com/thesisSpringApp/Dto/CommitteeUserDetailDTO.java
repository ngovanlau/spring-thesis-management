package com.thesisSpringApp.Dto;

import com.thesisSpringApp.pojo.User;
import lombok.Data;

@Data
public class CommitteeUserDetailDTO {
    private String role;
    private User user;
}
