package com.thesisSpringApp.Dto;

import lombok.Data;

import java.util.List;

@Data
public class UsersDTO {
    private List<UserDTO> academicManagers;
    private List<UserDTO> students;
    private List<UserDTO> lecturers;
}
