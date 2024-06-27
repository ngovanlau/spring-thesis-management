package com.thesisSpringApp.repository;

import java.util.List;

import com.thesisSpringApp.pojo.Faculty;

public interface FacultyRepository {
	List<Faculty> findAllFaculties();

	Faculty findFacultyById(int facultyId);
}
