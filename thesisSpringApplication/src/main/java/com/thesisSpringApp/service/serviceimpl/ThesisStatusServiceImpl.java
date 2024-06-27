package com.thesisSpringApp.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.ThesisStatus;
import com.thesisSpringApp.repository.ThesisStatusRepository;
import com.thesisSpringApp.service.ThesisStatusService;

@Service
public class ThesisStatusServiceImpl implements ThesisStatusService {
	@Autowired
	private ThesisStatusRepository thesisStatusRepository;

	@Override
	public ThesisStatus getThesisStatusById(int id) {

		return thesisStatusRepository.getThesisStatusById(id);
	}

}
