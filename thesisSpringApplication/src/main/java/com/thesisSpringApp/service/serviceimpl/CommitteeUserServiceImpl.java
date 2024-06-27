package com.thesisSpringApp.service.serviceimpl;

import java.util.List;

import com.thesisSpringApp.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.repository.CommitteeUserRepository;
import com.thesisSpringApp.service.CommitteeUserService;

@Service
public class CommitteeUserServiceImpl implements CommitteeUserService {

	@Autowired
	private CommitteeUserRepository committeeUserRepository;

	@Override
	public void saveCommitteeUser(CommitteeUser committeeUser) {
		committeeUserRepository.saveCommitteeUser(committeeUser);
	}

	@Override
	public List<CommitteeUser> getCommitteeUserByCommittee(Committee committee) {
		return committeeUserRepository.getCommitteeUserByCommittee(committee);
	}

	@Override
	public List<CommitteeUser> getAllUsersOfCommittee(int committeeId) {
		return committeeUserRepository.getAllUsersOfCommittee(committeeId);
	}

	@Override
	public CommitteeUser getCommitteeUser(int userId, int committeeId) {
		return committeeUserRepository.getCommitteeUser(userId, committeeId);
	}

	@Override
	public List<CommitteeUser> getCommitteeUserByUser(User user) {
		return committeeUserRepository.getCommitteeUserByUser(user);
	}
}
