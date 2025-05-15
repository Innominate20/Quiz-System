package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    Optional<Quiz> findByquizNameIgnoreCase(String name);
    long deleteBydeadlineBefore(LocalDate localDate);
}
