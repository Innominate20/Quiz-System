package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.QuizQuestionDto;
import com.example.Quiz_System.exception.QuizNotFoundException;
import com.example.Quiz_System.mapper.QuizQuestionMapper;
import com.example.Quiz_System.repository.QuizQuestionRepository;
import com.example.Quiz_System.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizQuestionService {

    private final QuizRepository quizRepository;
    private final QuizQuestionMapper quizQuestionMapper;
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionService(QuizRepository quizRepository, QuizQuestionMapper quizQuestionMapper, QuizQuestionRepository quizQuestionRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    public ResponseEntity<?> createQuizQuestions(List<QuizQuestionDto>list, String quizName){

        var quiz = quizRepository.findByquizNameIgnoreCase(quizName).orElseThrow(() -> new QuizNotFoundException("Quiz with the name : "+ quizName +" does not exist !"));

        var quizObjectList =list.stream().map(quizQuestionMapper::toQuizQuestion)
                .peek(quizObj -> quizObj.setQuiz(quiz))
                .toList();

        quizQuestionRepository.saveAll(quizObjectList);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz question added to quiz !");
    }
}
