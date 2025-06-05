package com.example.Quiz_System.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateQuestionDto {

    @NotNull
    private long id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctAnswer;
}
