package com.gamification.gamification.Repository;

import com.gamification.gamification.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value="select quiz_id,title,description from quizzes",nativeQuery = true)
    List<Map<String, String>> getquizs();


    @Query(value = "SELECT q.quiz_id, q.question_id, q.question_text, a.answer_id, a.answer_text, a.is_correct, qu.title, qu.description " +
            "FROM questions q JOIN answers a ON q.question_id = a.question_id " +
            "JOIN quizzes qu ON q.quiz_id = qu.quiz_id " +
            "WHERE qu.title = :title", nativeQuery = true)
    List<Map<String, Object>> getQuestionsByQuizId(String title);






}