package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizResultDto;
import com.example.Quiz_System.entity.Quiz;
import com.example.Quiz_System.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {

        this.quizService = quizService;
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> createNewQuiz(@Valid @RequestBody CreateQuizDto createQuizDto){

        return quizService.createQuiz(createQuizDto);
    }

    @GetMapping("/quizzes/{quizName}")
    public ResponseEntity<?> getQuiz(@PathVariable("quizName") String name){

        return quizService.getQuiz(name);
    }

    @PostMapping("/quizzes/{quizName}/results")
    public ResponseEntity<?> evaluateQuizResults(@PathVariable("quizName") String quizName, @RequestBody List<QuizResultDto> quizResultDtoList){

        return quizService.saveQuizResults(quizResultDtoList, quizName);
    }
}
