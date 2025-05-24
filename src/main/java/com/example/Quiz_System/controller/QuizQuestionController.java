package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.QuizQuestionDto;
import com.example.Quiz_System.entity.QuizQuestion;
import com.example.Quiz_System.service.QuizQuestionService;
import com.example.Quiz_System.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz-question")
public class QuizQuestionController {

    private final QuizQuestionService quizQuestionService;

    public QuizQuestionController(QuizQuestionService quizQuestionService) {
        this.quizQuestionService = quizQuestionService;
    }

    @PostMapping("/quiz-questions/{quizName}/{id}")
    public ResponseEntity<?> createNewQuizQuestions(@PathVariable("quizName") String quizName, @Valid @RequestBody List<QuizQuestionDto> quizQuestionDtoList, @PathVariable("id") long quizId){

        return quizQuestionService.addQuestionsToQuiz(quizQuestionDtoList, quizName, quizId);
    }

}
