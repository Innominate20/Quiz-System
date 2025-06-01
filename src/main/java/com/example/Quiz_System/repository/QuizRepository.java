package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    List<Quiz> findByquizNameIgnoreCase(String quizName);
    long deleteByexpirationBefore(LocalDate localDate);
    Optional<Quiz> findByquizNameIgnoreCaseAndId(String quizName, long id);
    @Query("SELECT q FROM Quiz q where (:quizName is null or q.quizName = :quizName)")
    Page<Quiz> findQuizzes(Pageable pageable, @Param("quizName")String quizName);
}
