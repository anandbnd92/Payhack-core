package com.gamification.gamification.Controller;

import com.gamification.gamification.Entity.Score;
import com.gamification.gamification.Entity.ScoreRequest;
import com.gamification.gamification.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @PostMapping("/scores")
    public ResponseEntity<Score> submitScore(@RequestBody ScoreRequest scoreRequest) {
        Score score = scoreService.saveScore(scoreRequest.getSubject(), scoreRequest.getTotalQuestions(), scoreRequest.getCorrectCount(), scoreRequest.getSubmittedUser());
        return ResponseEntity.ok(score);
    }


    @GetMapping("/getscore")
    public List<Map<String,String>> getscore()
    {
        return scoreService.getscore();
    }



}
