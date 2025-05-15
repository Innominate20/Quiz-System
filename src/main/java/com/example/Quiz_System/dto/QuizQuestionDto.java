package com.example.Quiz_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class QuizQuestionDto {
    @NotBlank
    private String question;
    @NotBlank
    private String optionA;
    @NotBlank
    private String optionB;
    @NotBlank
    private String optionC;
    @NotBlank
    private String optionD;
    @NotNull
    private char correctAnswer;
}
