package com.thesisSpringApp.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Faculty;
import com.thesisSpringApp.repository.FacultyRepository;
import com.thesisSpringApp.service.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	private FacultyRepository facultyRepository;

	@Override
	public List<Faculty> findAllFaculties() {
		return facultyRepository.findAllFaculties();
	}

	@Override
	public Faculty findFacultyById(int facultyId) {
		return facultyRepository.findFacultyById(facultyId);
	}

}
