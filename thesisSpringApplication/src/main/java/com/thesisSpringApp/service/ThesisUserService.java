package com.thesisSpringApp.service;

import java.util.List;
import java.util.Set;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;

public interface ThesisUserService {
	void addNewThesisUser(ThesisUser thesisUser, Thesis thesis, User user);

	List<ThesisUser> getUserByThesis(Thesis thesis);

	List<ThesisUser> getThesisByUser(User user);

	Set<ThesisUser> getStudentInThesisUsers();
}
