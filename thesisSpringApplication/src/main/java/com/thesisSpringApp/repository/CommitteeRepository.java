package com.thesisSpringApp.repository;

import java.util.List;
import java.util.Map;

import com.thesisSpringApp.pojo.Committee;

public interface CommitteeRepository {
	void saveCommittee(Committee committee);

	Committee getCommitteeById(int committeeId);

	List<Committee> getAllCommittee(Map<String, String> params);

	void deleteCommitteeById(int committeeId);

}
