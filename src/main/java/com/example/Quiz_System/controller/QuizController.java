package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizResultDto;
import com.example.Quiz_System.dto.QuizReviewDto;
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

    @GetMapping("quizzes/{quizName}/review")
    public ResponseEntity<List<QuizReviewDto>> reviewQuizzes(@PathVariable("quizName") String quizName){

        return quizService.reviewQuizzes(quizName);
    }

    @PostMapping("/quizzes")
    public ResponseEntity<?> createNewQuiz(@Valid @RequestBody CreateQuizDto createQuizDto){

        return quizService.createQuiz(createQuizDto);
    }

    @GetMapping("/quizzes/{quizName}/{id}")
    public ResponseEntity<?> takeQuiz(@PathVariable("quizName") String quizName, @PathVariable("id") long id){

        return quizService.takeQuiz(quizName, id);
    }

    @PostMapping("/quizzes/{quizName}/{id}/results")
    public ResponseEntity<?> saveQuizResults(@PathVariable("quizName") String quizName, @RequestBody List<QuizResultDto> quizResultDtoList, @PathVariable("id") long quizId){

        return quizService.saveQuizResults(quizResultDtoList, quizName, quizId);
    }
}
