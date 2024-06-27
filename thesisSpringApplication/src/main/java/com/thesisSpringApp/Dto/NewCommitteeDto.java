package com.thesisSpringApp.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCommitteeDto {
	private String name;
	private List<CommitteeUserDto> committeeUserDtos;
}
