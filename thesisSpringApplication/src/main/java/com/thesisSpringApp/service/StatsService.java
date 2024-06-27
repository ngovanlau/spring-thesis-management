package com.thesisSpringApp.service;

import java.util.List;

public interface StatsService {
	List<Object[]> statsThesisByYear(int year);

	List<Object[]> statsFrequencyJoinedThesisByYear(int year);

}
