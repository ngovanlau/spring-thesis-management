package com.thesisSpringApp.service.serviceimpl;

import com.thesisSpringApp.pojo.ThesisCommitteeRate;
import com.thesisSpringApp.repository.ThesisCommitteeRateRepository;
import com.thesisSpringApp.service.ThesisCommitteeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThesisCommitteeRateServiceImpl implements ThesisCommitteeRateService {
    @Autowired
    ThesisCommitteeRateRepository thesisCommitteeRateRepository;

    @Override
    public ThesisCommitteeRate getThesisCommitteeRateByThesisId(int thesisId) {
       return thesisCommitteeRateRepository.getThesisCommitteeRateByThesisId(thesisId);
    }

    @Override
    public List<ThesisCommitteeRate> getThesisCommitteeRatesByCommitteeId(int committeeId) {
        return thesisCommitteeRateRepository.getCommitteeRatesByCommitteeId(committeeId);
    }

    @Override
    public void saveAndUpdateThesisCommitteeRate(ThesisCommitteeRate thesisCommitteeRate) {
        thesisCommitteeRateRepository.saveAndUpdateThesisCommitteeRate(thesisCommitteeRate);
    }
}
