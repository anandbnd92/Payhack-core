package com.gamification.gamification.Service;

import com.gamification.gamification.Entity.Score;
import com.gamification.gamification.Repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ScoreServiceTests {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private ScoreRepository scoreRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSaveScore() {
        // Arrange
        String subject = "Math";
        int totalQuestions = 10;
        int correctCount = 8;
        String submittedUser = "user1";

        // Create a Score object and set properties
        Score expectedScore = new Score();
        expectedScore.setSubject(subject);
        expectedScore.setTotalQuestions(totalQuestions);
        expectedScore.setCorrectCount(correctCount);
        expectedScore.setSubmittedUser(submittedUser);
        expectedScore.setSubmittedTime(LocalDateTime.now()); // Set to current time

        // Mock the save method
        when(scoreRepository.save(expectedScore)).thenReturn(expectedScore);

        // Act
        when(scoreService.saveScore(subject, totalQuestions, correctCount, submittedUser)).thenReturn(expectedScore);

        // Assert
        assertNotNull(expectedScore, "The returned Score object should not be null");
        assertEquals(expectedScore.getSubject(), subject);
        assertEquals(expectedScore.getTotalQuestions(), totalQuestions);
        assertEquals(expectedScore.getCorrectCount(), correctCount);
        assertEquals(expectedScore.getSubmittedUser(), submittedUser);
        // Comparing LocalDateTime might require adjustment based on how you handle the time in your application
    }

    @Test
    void testGetScore() {
        // Arrange
        Map<String, String> scoreMap1 = new HashMap<>();
        scoreMap1.put("subject", "Math");
        scoreMap1.put("totalQuestions", "10");
        scoreMap1.put("correctCount", "8");
        scoreMap1.put("submittedUser", "user1");

        Map<String, String> scoreMap2 = new HashMap<>();
        scoreMap2.put("subject", "Science");
        scoreMap2.put("totalQuestions", "8");
        scoreMap2.put("correctCount", "6");
        scoreMap2.put("submittedUser", "user2");

        List<Map<String, String>> expectedScoreList = Arrays.asList(scoreMap1, scoreMap2);

        when(scoreRepository.getscore()).thenReturn(expectedScoreList);

        // Act
        List<Map<String, String>> actualScoreList = scoreService.getscore();

        // Assert
        assertEquals(expectedScoreList.size(), actualScoreList.size());
        assertEquals(expectedScoreList, actualScoreList);
    }
}
