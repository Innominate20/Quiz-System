package com.example.Quiz_System.mapper;

import com.example.Quiz_System.dto.QuizQuestionDto;
import com.example.Quiz_System.dto.QuizQuestionResponseDto;
import com.example.Quiz_System.dto.UpdateQuestionDto;
import com.example.Quiz_System.entity.QuizQuestion;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface QuizQuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    QuizQuestion toQuizQuestion(QuizQuestionDto quizQuestionDto);

    QuizQuestionResponseDto toQuizQuestionResponseDto(QuizQuestion quizQuestion);

    void updateQuestionFromDto(UpdateQuestionDto updateQuestionDto, @MappingTarget QuizQuestion quizQuestion);
}
