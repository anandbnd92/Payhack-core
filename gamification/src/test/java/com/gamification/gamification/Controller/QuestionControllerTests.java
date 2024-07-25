package com.gamification.gamification.Controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import com.gamification.gamification.Entity.AnswerDto;
import com.gamification.gamification.Entity.QuestionDto;
import com.gamification.gamification.Service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(QuestionController.class)
@EnableWebMvc
public class QuestionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    void testGetquizs() throws Exception {
        // Given
        List<Map<String, String>> mockQuizList = List.of(
                Map.of("title", "Math"),
                Map.of("title", "Science")
        );
        given(questionService.getquizs()).willReturn(mockQuizList);

        // When & Then
        mockMvc.perform(get("/subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Math"))
                .andExpect(jsonPath("$[1].title").value("Science"));
    }

    @Test
    void testGetQuestionsByQuizId() throws Exception {
        // Prepare mock data
        List<AnswerDto> answers1 = Arrays.asList(new AnswerDto("4", true), new AnswerDto("5", false));
        List<AnswerDto> answers2 = Arrays.asList(new AnswerDto("Paris", true), new AnswerDto("London", false));

        QuestionDto question1 = new QuestionDto("What is 2+2?", answers1);
        QuestionDto question2 = new QuestionDto("What is the capital of France?", answers2);

        Map<String, List<QuestionDto>> mockQuestionMap = new HashMap<>();
        mockQuestionMap.put("Math", Arrays.asList(question1, question2));

        given(questionService.getQuestionsByQuizId("Math")).willReturn(mockQuestionMap);

        // Perform the test
        mockMvc.perform(get("/questions/Math")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Math[0].questionText").value("What is 2+2?"))
                .andExpect(jsonPath("$.Math[0].answerOptions[0].answerText").value("4"))
                .andExpect(jsonPath("$.Math[0].answerOptions[1].answerText").value("5"))
                .andExpect(jsonPath("$.Math[1].questionText").value("What is the capital of France?"))
                .andExpect(jsonPath("$.Math[1].answerOptions[0].answerText").value("Paris"))
                .andExpect(jsonPath("$.Math[1].answerOptions[1].answerText").value("London"));
    }
}
