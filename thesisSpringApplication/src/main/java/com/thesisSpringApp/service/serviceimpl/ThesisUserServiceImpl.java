package com.thesisSpringApp.service.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.repository.ThesisUserRepository;
import com.thesisSpringApp.service.ThesisUserService;

@Service
public class ThesisUserServiceImpl implements ThesisUserService {

	@Autowired
	private ThesisUserRepository thesisUserRepository;

	@Override
	public void addNewThesisUser(ThesisUser thesisUser, Thesis thesis, User user) {
		thesisUserRepository.saveThesisUser(thesisUser, thesis, user);
	}

	@Override
	public List<ThesisUser> getUserByThesis(Thesis thesis) {
		return thesisUserRepository.getUserByThesis(thesis);
	}

	@Override
	public List<ThesisUser> getThesisByUser(User user) {
		return thesisUserRepository.getThesisByUser(user);
	}

	@Override
	public Set<ThesisUser> getStudentInThesisUsers() {
		List<ThesisUser> thesisUsers = thesisUserRepository.getThesisUser();

		Set<ThesisUser> thesisUserSet = thesisUsers.stream().filter(
				thesisUser -> {
                    return thesisUser.getUserId().getRoleId().getName().equals("ROLE_SINHVIEN");
				}
		).collect(Collectors.toSet());

		return thesisUserSet;
	}

}
