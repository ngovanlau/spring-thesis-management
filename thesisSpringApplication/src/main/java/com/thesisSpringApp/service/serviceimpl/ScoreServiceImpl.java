package com.thesisSpringApp.service.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.repository.ScoreRepository;
import com.thesisSpringApp.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void saveAndUpdateScore(Score score) {
        scoreRepository.saveAndUpdateScore(score);
    }

    @Override
    public Score getScore(int thesisId, int committeeUserId, int criteriaId) {
        return scoreRepository.getScore(thesisId, committeeUserId, criteriaId);
    }

    @Override
    public List<Score> getScoresByThesisId(int thesisId) {
        return scoreRepository.getScoresByThesisId(thesisId);
    }

    @Override
    public boolean isScoring(int thesisId, int committeeUserId) {

        List<Score> scores = scoreRepository.getScoreOfCommitteeUser(thesisId, committeeUserId);

        if (scores == null || scores.isEmpty()) {
            return false;
        }

        return true;
    }

	@Override
	public List<Score> getScoreOfCommitteeUserId(int committeeUserId) {
		return scoreRepository.getScoreOfCommitteeUserId(committeeUserId);
	}
}
