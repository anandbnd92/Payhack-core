package com.gamification.gamification.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.gamification.gamification.Entity.QuestionDto;
import com.gamification.gamification.Service.QuestionService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/subjects")
    public List<Map<String,String>> getquizs()
    {
        return questionService.getquizs();
    }

    @GetMapping("/questions/{title}")
    public Map<String, List<QuestionDto>> getQuestionsByQuizId(@PathVariable String title) {
        return questionService.getQuestionsByQuizId(title);
    }


}
