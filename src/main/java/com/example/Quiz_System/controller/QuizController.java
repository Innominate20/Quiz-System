package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizResultDto;
import com.example.Quiz_System.dto.QuizResultResponseDto;
import com.example.Quiz_System.dto.QuizReviewDto;
import com.example.Quiz_System.entity.Quiz;
import com.example.Quiz_System.entity.QuizResult;
import com.example.Quiz_System.service.QuizResultService;
import com.example.Quiz_System.service.QuizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;
    private final QuizResultService quizResultService;

    @Autowired
    public QuizController(QuizService quizService, QuizResultService quizResultService) {

        this.quizService = quizService;
        this.quizResultService = quizResultService;
    }

    @GetMapping("/quizzes/review")
    public ResponseEntity<Page<QuizReviewDto>> reviewQuizzesToTake(
            @RequestParam(required = false) String quizName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){

        return quizService.reviewAllQuizzes(page,size,quizName,sortBy);
    }

    @GetMapping("quizzes/me/{quizName}/review")
    public ResponseEntity<List<QuizReviewDto>> reviewQuizzes(@PathVariable("quizName") String quizName){

        return quizService.reviewMyQuizzes(quizName);
    }

    @GetMapping("quizzes/me/result/review")
    public ResponseEntity<List<QuizResultResponseDto>> reviewQuizResults(@RequestParam(required = false) String quizName){

        return quizResultService.reviewResults(quizName);
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
