package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findAllById(List<Long>ids);

}
