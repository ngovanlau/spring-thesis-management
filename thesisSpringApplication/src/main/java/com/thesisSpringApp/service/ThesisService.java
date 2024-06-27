package com.thesisSpringApp.service;

import java.util.List;
import java.util.Map;

import com.thesisSpringApp.pojo.Thesis;

public interface ThesisService {
	void saveAndUpdateThesis(Thesis thesis);

	Thesis getThesisById(int id);

	List<Thesis> getAllThesis(Map<String, String> params);

	int totalPages();

	void deleteThesisById(int id);

}
