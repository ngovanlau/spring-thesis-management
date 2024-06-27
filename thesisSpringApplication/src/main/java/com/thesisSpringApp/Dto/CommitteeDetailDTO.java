package com.thesisSpringApp.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CommitteeDetailDTO {
    private int id;
    private String name;
    private boolean active;
    private List<CommitteeUserDetailDTO> members;
}
