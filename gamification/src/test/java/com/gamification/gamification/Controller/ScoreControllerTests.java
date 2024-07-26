package com.gamification.gamification.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamification.gamification.Entity.Score;
import com.gamification.gamification.Entity.ScoreRequest;
import com.gamification.gamification.Service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ScoreControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private ScoreController scoreController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scoreController).build();
        objectMapper = new ObjectMapper(); // Initialize ObjectMapper for JSON serialization
    }

    @Test
    public void testSubmitScore() throws Exception {
        ScoreRequest scoreRequest = new ScoreRequest("Math", 10, 8, "user123");
        Score score = new Score();
        score.setId(1L);
        score.setSubject("Math");
        score.setTotalQuestions(10);
        score.setCorrectCount(8);
        score.setSubmittedUser("user123");
        score.setSubmittedTime(LocalDateTime.now()); // Setting the current time for testing

        when(scoreService.saveScore(any(String.class), any(Integer.class), any(Integer.class), any(String.class)))
                .thenReturn(score);

        mockMvc.perform(post("/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.subject", is("Math")))
                .andExpect(jsonPath("$.totalQuestions", is(10)))
                .andExpect(jsonPath("$.correctCount", is(8)))
                .andExpect(jsonPath("$.submittedUser", is("user123")))
                .andExpect(jsonPath("$.submittedTime").exists()); // Check if submittedTime is present
    }

    @Test
    public void testGetscore() throws Exception {
        Map<String, String> scoreMap = new HashMap<>();
        scoreMap.put("subject", "Math");
        scoreMap.put("totalQuestions", "10");
        scoreMap.put("correctCount", "8");
        scoreMap.put("submittedUser", "user123");
        scoreMap.put("submittedTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // Formatting LocalDateTime to ISO string

        List<Map<String, String>> scoreList = Collections.singletonList(scoreMap);

        when(scoreService.getscore()).thenReturn(scoreList);

        mockMvc.perform(get("/getscore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].subject", is("Math")))
                .andExpect(jsonPath("$[0].totalQuestions", is("10")))
                .andExpect(jsonPath("$[0].correctCount", is("8")))
                .andExpect(jsonPath("$[0].submittedUser", is("user123")))
                .andExpect(jsonPath("$[0].submittedTime").exists()); // Check if submittedTime is present
    }
}