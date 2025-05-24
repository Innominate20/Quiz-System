package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    List<Quiz> findByquizNameIgnoreCase(String quizName);
    long deleteByexpirationBefore(LocalDate localDate);
    Optional<Quiz> findByquizNameIgnoreCaseAndId(String quizName, long id);
}
