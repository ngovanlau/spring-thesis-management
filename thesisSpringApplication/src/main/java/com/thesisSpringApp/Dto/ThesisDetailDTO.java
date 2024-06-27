package com.thesisSpringApp.Dto;

import java.util.List;

import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThesisDetailDTO {
	private Thesis thesis;
	private Committee committee;
	private List<User> students;
	private List<User> lecturers;
	private List<ScoreDetailDTO> scores;
}