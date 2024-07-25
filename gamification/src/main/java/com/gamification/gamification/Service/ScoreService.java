package com.gamification.gamification.Service;

import com.gamification.gamification.Entity.Score;
import com.gamification.gamification.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class ScoreService {


    @Autowired
    private ScoreRepository scoreRepository;

    public Score saveScore(String subject, int totalQuestions, int correctCount, String submittedUser) {
        Score score = new Score();
        score.setSubject(subject);
        score.setTotalQuestions(totalQuestions);
        score.setCorrectCount(correctCount);
        score.setSubmittedTime(LocalDateTime.now());
        score.setSubmittedUser(submittedUser);
        return scoreRepository.save(score);
    }

    public List<Map<String, String>> getscore() {
        return scoreRepository.getscore();
    }
}
