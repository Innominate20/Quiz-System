package com.example.Quiz_System.entity;

import com.example.Quiz_System.entity.user.QuizTaker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "quiz_results")
@AllArgsConstructor
@Builder
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quiz_name")
    private String quizName;
    private double mark;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private QuizTaker quizTaker;
}
