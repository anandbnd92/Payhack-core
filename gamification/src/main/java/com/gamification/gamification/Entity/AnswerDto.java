package com.gamification.gamification.Entity;

public class AnswerDto {
    private String answerText;
    private boolean correct;

    // Constructors, getters and setters
    public AnswerDto(String answerText, boolean correct) {
        this.answerText = answerText;
        this.correct = correct;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}