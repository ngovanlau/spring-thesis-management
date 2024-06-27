package com.thesisSpringApp.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.repository.StatsRepository;
import com.thesisSpringApp.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	private StatsRepository statsRepository;

	@Override
	public List<Object[]> statsThesisByYear(int year) {
		return statsRepository.statsThesisByYear(year);
	}

	@Override
	public List<Object[]> statsFrequencyJoinedThesisByYear(int year) {
		return statsRepository.statsFrequencyJoinedThesisByYear(year);
	}

}
