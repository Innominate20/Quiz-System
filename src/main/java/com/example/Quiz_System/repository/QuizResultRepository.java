package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Optional<QuizResult> findByQuizTakerAndQuizTaker(Long id, String quizName);
}
