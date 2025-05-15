package com.example.Quiz_System.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quiz_questions")
@Getter
@Setter
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctAnswer;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
