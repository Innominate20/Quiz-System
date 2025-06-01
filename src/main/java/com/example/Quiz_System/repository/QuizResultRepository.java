package com.example.Quiz_System.repository;

import com.example.Quiz_System.entity.QuizResult;
import com.example.Quiz_System.entity.user.QuizTaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Optional<QuizResult> findByQuizTakerAndQuizNameIgnoreCase(QuizTaker quizTaker, String quizName);

    @Query("SELECT e FROM QuizResult e WHERE (:quizName is null OR e.quizName = :quizName) AND e.quizTaker = :quizTaker")
    List<QuizResult> returnQuizResults(@Param("quizName")String quizName, @Param("quizTaker") QuizTaker quizTaker);
}
