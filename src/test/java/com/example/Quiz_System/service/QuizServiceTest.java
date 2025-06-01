package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.QuizReviewDto;
import com.example.Quiz_System.entity.Quiz;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.entity.user.User;
import com.example.Quiz_System.mapper.QuizMapper;
import com.example.Quiz_System.repository.QuizRepository;
import com.example.Quiz_System.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuizMapper quizMapper;

    @InjectMocks
    private QuizService quizService;


    @Test
    void reviewMyQuizzesTest(){

        String userEmail = "test@gmail.com";
        QuizCreator user  = new QuizCreator();
        user.setId(100);
        user.setEmail(userEmail);

        Quiz quiz = Quiz.builder()
                .quizName("Math-quiz")
                .quizCreator((QuizCreator) user)
                .build();

        user.setQuizzes(List.of(quiz));

        QuizReviewDto quizReviewDto = new QuizReviewDto();
        quizReviewDto.setQuizOwner(userEmail);


        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(userEmail);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));

        Mockito.when(quizRepository.findByquizNameIgnoreCase("Math-quiz")).thenReturn(List.of(quiz));
        Mockito.when(quizMapper.toQuizReviewDto(quiz)).thenReturn(quizReviewDto);


        var response = quizService.reviewMyQuizzes("Math-quiz");


        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());

    }
}
