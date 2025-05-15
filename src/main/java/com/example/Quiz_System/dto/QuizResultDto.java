package com.example.Quiz_System.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuizResultDto {
    @NotNull
    private long id;
    @NotNull
    private char correctAnswer;
}
