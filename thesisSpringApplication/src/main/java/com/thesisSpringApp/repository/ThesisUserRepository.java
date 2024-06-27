package com.thesisSpringApp.repository;

import java.util.List;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;

public interface ThesisUserRepository {
	void saveThesisUser(ThesisUser thesisUser, Thesis thesis, User user);

	List<ThesisUser> getUserByThesis(Thesis thesis);

	List<ThesisUser> getThesisByUser(User user);

	List<ThesisUser> getThesisUser();
}
