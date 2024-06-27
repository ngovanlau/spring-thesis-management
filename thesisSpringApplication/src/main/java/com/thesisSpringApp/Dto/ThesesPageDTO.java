package com.thesisSpringApp.Dto;

import com.thesisSpringApp.pojo.Thesis;
import lombok.Data;

import java.util.List;

@Data
public class ThesesPageDTO {
    private int totalPages;
    private List<Thesis> result;
}
