package com.thesisSpringApp.service;

import java.util.List;

import com.thesisSpringApp.pojo.Score;

public interface ScoreService {
    void saveAndUpdateScore(Score score);

    Score getScore(int thesisId, int committeeUserId, int criteriaId);

    List<Score> getScoresByThesisId(int thesisId);

    boolean isScoring(int thesisId, int committeeUserId);

	List<Score> getScoreOfCommitteeUserId(int committeeUserId);

}
