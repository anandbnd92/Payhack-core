package com.gamification.gamification.Service;

import com.gamification.gamification.Entity.AnswerDto;
import com.gamification.gamification.Entity.QuestionDto;
import com.gamification.gamification.Repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class QuestionServiceTests {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetQuestionsByQuizId() {
        // Arrange
        String quizTitle = "Sample Quiz";

        Map<String, Object> rawDataItem1 = new HashMap<>();
        rawDataItem1.put("title", quizTitle);
        rawDataItem1.put("question_id", 1L);
        rawDataItem1.put("question_text", "What is 2 + 2?");
        rawDataItem1.put("answer_text", "4");
        rawDataItem1.put("is_correct", true);

        Map<String, Object> rawDataItem2 = new HashMap<>();
        rawDataItem2.put("title", quizTitle);
        rawDataItem2.put("question_id", 1L);
        rawDataItem2.put("question_text", "What is 2 + 2?");
        rawDataItem2.put("answer_text", "5");
        rawDataItem2.put("is_correct", false);

        Map<String, Object> rawDataItem3 = new HashMap<>();
        rawDataItem3.put("title", quizTitle);
        rawDataItem3.put("question_id", 2L);
        rawDataItem3.put("question_text", "What is the capital of France?");
        rawDataItem3.put("answer_text", "Paris");
        rawDataItem3.put("is_correct", true);

        List<Map<String, Object>> rawData = Arrays.asList(rawDataItem1, rawDataItem2, rawDataItem3);

        when(questionRepository.getQuestionsByQuizId(anyString())).thenReturn(rawData);

        // Act
        Map<String, List<QuestionDto>> result = questionService.getQuestionsByQuizId(quizTitle);

        // Assert
        assertThat(result, hasKey(quizTitle));
        List<QuestionDto> questions = result.get(quizTitle);
        assertThat(questions, hasSize(2)); // Two questions

        QuestionDto question1 = questions.stream()
                .filter(q -> q.getQuestionText().equals("What is 2 + 2?"))
                .findFirst()
                .orElse(null);
        assertNotNull(question1);
        assertThat(question1.getAnswerOptions(), hasSize(2));

        QuestionDto question2 = questions.stream()
                .filter(q -> q.getQuestionText().equals("What is the capital of France?"))
                .findFirst()
                .orElse(null);
        assertNotNull(question2);
        assertThat(question2.getAnswerOptions(), hasSize(1));
    }

    @Test
    public void testGetQuestionsByQuizId_NoQuestions() {
        // Arrange
        String quizTitle = "Nonexistent Quiz";
        when(questionRepository.getQuestionsByQuizId(anyString())).thenReturn(Collections.emptyList());

        // Act
        Map<String, List<QuestionDto>> result = questionService.getQuestionsByQuizId(quizTitle);

        // Assert
        assertThat(result, is(emptyMap()));
    }

    @Test
    public void testGetquizs() {
        // Arrange
        Map<String, String> quizMap1 = new HashMap<>();
        quizMap1.put("title", "Quiz 1");

        Map<String, String> quizMap2 = new HashMap<>();
        quizMap2.put("title", "Quiz 2");

        List<Map<String, String>> quizList = Arrays.asList(quizMap1, quizMap2);

        when(questionRepository.getquizs()).thenReturn(quizList);

        // Act
        List<Map<String, String>> result = questionService.getquizs();

        // Assert
        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(
                allOf(hasEntry("title", "Quiz 1")),
                allOf(hasEntry("title", "Quiz 2"))
        ));
    }
}
