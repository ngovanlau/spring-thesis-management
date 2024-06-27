package com.thesisSpringApp.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.thesisSpringApp.pojo.Faculty;

public class FacultyFormatter implements Formatter<Faculty> {

	@Override
	public String print(Faculty object, Locale locale) {
		return String.valueOf(object.getId());
	}

	@Override
	public Faculty parse(String facultyId, Locale locale) throws ParseException {
		Faculty faculty = new Faculty();
		faculty.setId(Integer.parseInt(facultyId));
		return faculty;
	}

}
