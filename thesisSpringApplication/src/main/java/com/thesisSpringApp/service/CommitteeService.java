package com.thesisSpringApp.service;

import java.util.List;
import java.util.Map;

import com.thesisSpringApp.pojo.Committee;

public interface CommitteeService {
	void saveCommittee(Committee committee);

	Committee getCommitteeById(int committeeId);

	List<Committee> getAllCommittee(Map<String, String> params);

	List<Committee> getCommitteesForThesis();

	Committee getCommitteeOfThesis(int thesisId);

	int totalCommitteePages();

	void deleteCommitteeById(int committeeId);

}
