package com.gamification.gamification.Entity;

import java.util.List;

public class QuestionDto {
    private String questionText;
    private List<AnswerDto> answerOptions;

    // Constructors, getters and setters
    public QuestionDto(String questionText, List<AnswerDto> answerOptions) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<AnswerDto> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerDto> answerOptions) {
        this.answerOptions = answerOptions;
    }
}