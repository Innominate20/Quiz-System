package com.example.Quiz_System.mapper;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizReviewDto;
import com.example.Quiz_System.entity.Quiz;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "quizQuestions", ignore = true)
    @Mapping(target = "quizCreator", ignore = true)
    Quiz toQuiz(CreateQuizDto createQuizDto);

    @AfterMapping
    default void enhanceQuiz(@MappingTarget Quiz quiz){
        quiz.setQuizQuestions(new ArrayList<>());
        quiz.setCreatedAt(LocalDate.now());
    }

    @Mapping(target = "quizOwner", ignore = true)
    QuizReviewDto toQuizReviewDto(Quiz quiz);


}
