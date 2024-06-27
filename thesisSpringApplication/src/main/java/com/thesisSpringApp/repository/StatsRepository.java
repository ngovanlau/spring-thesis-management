package com.thesisSpringApp.repository;

import java.util.List;

public interface StatsRepository {
	List<Object[]> statsThesisByYear(int year);

	List<Object[]> statsFrequencyJoinedThesisByYear(int year);
}
