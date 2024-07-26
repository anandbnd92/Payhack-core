package com.gamification.gamification.Repository;

import com.gamification.gamification.Entity.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@SpringJUnitConfig
public class ScoreRepositoryTests {

    @Autowired
    private ScoreRepository scoreRepository;

    @BeforeEach
    void setUp() {
        // Optional: Add setup code to initialize the database state if necessary.
    }
//
//    @Test
//    void testGetScore() {
//        // Arrange: Insert some test data
//        Score score1 = new Score();
//        score1.setSubject("Math");
//        score1.setTotalQuestions(10);
//        score1.setCorrectCount(8);
//        score1.setSubmittedTime(LocalDateTime.now());
//        score1.setSubmittedUser("user1");
//        scoreRepository.save(score1);
//
//        Score score2 = new Score();
//        score2.setSubject("Science");
//        score2.setTotalQuestions(15);
//        score2.setCorrectCount(12);
//        score2.setSubmittedTime(LocalDateTime.now());
//        score2.setSubmittedUser("user2");
//        scoreRepository.save(score2);
//
//        // Act: Call the method
//        List<Map<String, String>> scores = scoreRepository.getscore();
//
//        // Assert: Verify the results
//        assertEquals(2, scores.size());
//
//        Map<String, String> scoreMap1 = scores.get(0);
//        assertEquals("Math", scoreMap1.get("subject"));
//        assertEquals("10", scoreMap1.get("totalquestions"));
//        assertEquals("8", scoreMap1.get("correctcount"));
//
//        Map<String, String> scoreMap2 = scores.get(1);
//        assertEquals("Science", scoreMap2.get("subject"));
//        assertEquals("15", scoreMap2.get("totalquestions"));
//        assertEquals("12", scoreMap2.get("correctcount"));
//    }
}
