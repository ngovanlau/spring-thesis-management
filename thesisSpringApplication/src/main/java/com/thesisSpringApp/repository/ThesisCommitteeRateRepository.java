package com.thesisSpringApp.repository;

import com.thesisSpringApp.pojo.ThesisCommitteeRate;

import java.util.List;

public interface ThesisCommitteeRateRepository {
    ThesisCommitteeRate getThesisCommitteeRateByThesisId(int thesisId);

    List<ThesisCommitteeRate> getCommitteeRatesByCommitteeId(int committeeId);

    void saveAndUpdateThesisCommitteeRate(ThesisCommitteeRate thesisCommitteeRate);
}
