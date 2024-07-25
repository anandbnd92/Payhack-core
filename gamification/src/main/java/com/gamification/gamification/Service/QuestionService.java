package com.gamification.gamification.Service;

import com.gamification.gamification.Entity.Answer;
import com.gamification.gamification.Entity.AnswerDto;
import com.gamification.gamification.Entity.Question;
import com.gamification.gamification.Entity.QuestionDto;
import com.gamification.gamification.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Map<String,String>> getquizs()
    {
        return  questionRepository.getquizs();
    }



    public Map<String, List<QuestionDto>> getQuestionsByQuizId(String title) {
        List<Map<String, Object>> rawData = questionRepository.getQuestionsByQuizId(title);

        // Use a Map to group questions by their question_id
        Map<Long, QuestionDto> questionMap = new HashMap<>();

        for (Map<String, Object> item : rawData) {
            Long questionId = ((Number) item.get("question_id")).longValue();
            String questionText = (String) item.get("question_text");

            // Safely handle the "is_correct" value based on its actual type
            Object isCorrectObj = item.get("is_correct");
            boolean isCorrect;
            if (isCorrectObj instanceof Boolean) {
                isCorrect = (Boolean) isCorrectObj;
            } else if (isCorrectObj instanceof String) {
                isCorrect = Boolean.parseBoolean((String) isCorrectObj);
            } else if (isCorrectObj instanceof Number) {
                isCorrect = ((Number) isCorrectObj).intValue() != 0;
            } else {
                // Handle unexpected types
                throw new IllegalArgumentException("Unexpected type for 'is_correct': " + isCorrectObj.getClass().getName());
            }

            // Create an AnswerDto object
            AnswerDto answerOption = new AnswerDto((String) item.get("answer_text"), isCorrect);

            // Check if the question already exists in the map
            if (questionMap.containsKey(questionId)) {
                // If the question exists, add the answer option to the existing list
                questionMap.get(questionId).getAnswerOptions().add(answerOption);
            } else {
                // If the question does not exist, create a new QuestionDto and add it to the map
                List<AnswerDto> answerOptions = new ArrayList<>();
                answerOptions.add(answerOption);
                QuestionDto questionDto = new QuestionDto(questionText, answerOptions);
                questionMap.put(questionId, questionDto);
            }
        }

        // Group questions by quiz title
        Map<String, List<QuestionDto>> groupedQuestions = new HashMap<>();
        for (Map<String, Object> item : rawData) {
            String quizTitle = (String) item.get("title");
            Long questionId = ((Number) item.get("question_id")).longValue();
            QuestionDto questionDto = questionMap.get(questionId);

            if (!groupedQuestions.containsKey(quizTitle)) {
                groupedQuestions.put(quizTitle, new ArrayList<>());
            }

            // Ensure the question is not added multiple times
            if (!groupedQuestions.get(quizTitle).contains(questionDto)) {
                groupedQuestions.get(quizTitle).add(questionDto);
            }
        }

        return groupedQuestions;
    }

}
