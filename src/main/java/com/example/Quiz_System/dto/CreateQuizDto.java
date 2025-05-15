package com.example.Quiz_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreateQuizDto {

    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime deadline;
    @NotNull
    private double duration;
    @NotBlank
    private String quizName;

}
