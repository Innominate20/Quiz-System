package com.example.Quiz_System.mapper;

import com.example.Quiz_System.dto.QuizResultResponseDto;
import com.example.Quiz_System.entity.QuizResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizResultMapper {

    QuizResultResponseDto toQuizResultResponseDto(QuizResult quizResult);
}
