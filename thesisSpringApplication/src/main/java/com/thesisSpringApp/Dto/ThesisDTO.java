package com.thesisSpringApp.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThesisDTO {
	private String name;
	private List<Integer> userIds;
}
