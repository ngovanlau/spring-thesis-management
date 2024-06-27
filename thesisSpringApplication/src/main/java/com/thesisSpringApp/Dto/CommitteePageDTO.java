package com.thesisSpringApp.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CommitteePageDTO {
    private int totalPages;
    private List<CommitteeDetailDTO> result;
}
