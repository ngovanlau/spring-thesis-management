package com.thesisSpringApp.service;

import java.util.List;

import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.pojo.User;

public interface CommitteeUserService {
	void saveCommitteeUser(CommitteeUser committeeUser);

	List<CommitteeUser> getCommitteeUserByCommittee(Committee committee);

	List<CommitteeUser> getAllUsersOfCommittee(int committeeId);

	CommitteeUser getCommitteeUser(int userId, int committeeId);

	List<CommitteeUser> getCommitteeUserByUser(User user);
}
