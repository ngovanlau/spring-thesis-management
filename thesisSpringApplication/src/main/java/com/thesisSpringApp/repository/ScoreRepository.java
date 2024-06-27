package com.thesisSpringApp.repository;

import java.util.List;

import com.thesisSpringApp.pojo.Score;

public interface ScoreRepository {
    void saveAndUpdateScore(Score score);

    Score getScore(int thesisId, int committeeUserId, int criteriaId);

    List<Score> getScoresByThesisId(int thesisId);

    List<Score> getScoreOfCommitteeUser(int thesisId, int committeeUserId);

	List<Score> getScoreOfCommitteeUserId(int committeeUserId);
}
