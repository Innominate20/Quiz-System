package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.QuizResultResponseDto;
import com.example.Quiz_System.entity.user.QuizTaker;
import com.example.Quiz_System.exception.QuizResultNotFoundException;
import com.example.Quiz_System.mapper.QuizResultMapper;
import com.example.Quiz_System.repository.QuizResultRepository;
import com.example.Quiz_System.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultService {

    private final QuizResultMapper quizResultMapper;
    private final UserRepository userRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizResultService(QuizResultMapper quizResultMapper, UserRepository userRepository, QuizResultRepository quizResultRepository) {
        this.quizResultMapper = quizResultMapper;
        this.userRepository = userRepository;
        this.quizResultRepository = quizResultRepository;
    }

    public ResponseEntity<List<QuizResultResponseDto>> reviewResults(String quizName){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = (QuizTaker) userRepository.findByEmail(userEmail).get();

        var resultList = quizResultRepository.returnQuizResults(quizName,user);

        if (resultList.isEmpty()){

            throw new QuizResultNotFoundException("No quiz results found !");
        }

        var resultResponse = resultList.stream()
                .map(quizResultMapper::toQuizResultResponseDto)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
