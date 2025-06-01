package com.example.Quiz_System.dto;

import com.example.Quiz_System.entity.user.QuizTaker;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Setter
public class QuizResultResponseDto {

    private String quizName;
    private double mark;
}
