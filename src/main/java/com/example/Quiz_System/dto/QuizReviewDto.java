package com.example.Quiz_System.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class QuizReviewDto {
    private long id;
    private String quizName;
    private double duration;
    private String quizOwner;
    private LocalDateTime startDate;
    private LocalDate createdAt;
}
