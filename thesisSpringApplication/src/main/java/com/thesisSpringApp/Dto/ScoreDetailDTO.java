package com.thesisSpringApp.Dto;

import lombok.Data;

@Data
public class ScoreDetailDTO {
    private int id;
    private int criteriaId;
    private int userId;
    private float score;
}
