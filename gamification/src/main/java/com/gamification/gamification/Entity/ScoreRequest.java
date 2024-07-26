package com.gamification.gamification.Entity;

public class ScoreRequest {
    private String subject;
    private int totalQuestions;
    private int correctCount;
    private String submittedUser;

    public ScoreRequest(String subject, int totalQuestions, int correctCount, String submittedUser) {
        this.subject = subject;
        this.totalQuestions = totalQuestions;
        this.correctCount = correctCount;
        this.submittedUser = submittedUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public String getSubmittedUser() {
        return submittedUser;
    }

    public void setSubmittedUser(String submittedUser) {
        this.submittedUser = submittedUser;
    }
}
