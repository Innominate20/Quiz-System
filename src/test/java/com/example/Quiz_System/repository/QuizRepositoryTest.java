package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.Quiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class QuizRepositoryTest {

    @Autowired
    private QuizRepository quizRepository;


    @Test
    void testFindByQuizNameIgnoreCase(){

        Quiz quiz = Quiz.builder()
                .quizName("Math-quiz")
                .build();

        quizRepository.save(quiz);

        var queriedQuiz = quizRepository.findByquizNameIgnoreCase("Math-quiz");

        Assertions.assertFalse(queriedQuiz.isEmpty());
    }

}
