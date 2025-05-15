package com.example.Quiz_System.mapper;

import com.example.Quiz_System.dto.QuizQuestionDto;
import com.example.Quiz_System.dto.QuizQuestionResponseDto;
import com.example.Quiz_System.entity.QuizQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizQuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    QuizQuestion toQuizQuestion(QuizQuestionDto quizQuestionDto);

    QuizQuestionResponseDto toQuizQuestionResponseDto(QuizQuestion quizQuestion);

}
