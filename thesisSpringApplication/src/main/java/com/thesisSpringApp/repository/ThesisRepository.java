package com.thesisSpringApp.repository;

import java.util.List;
import java.util.Map;

import com.thesisSpringApp.pojo.Thesis;

public interface ThesisRepository {
	void saveAndUpdateThesis(Thesis thesis);

	Thesis getThesisById(int id);

	List<Thesis> getAllThesis(Map<String, String> params);

	void deleteThesisById(int id);

}
