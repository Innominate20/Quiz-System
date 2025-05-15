package com.example.Quiz_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class QuizQuestionResponseDto {

    private long id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

}
