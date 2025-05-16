package com.example.Quiz_System.mapper;

import com.example.Quiz_System.dto.UserRegisterRequestDto;
import com.example.Quiz_System.entity.user.Admin;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.entity.user.QuizTaker;
import com.example.Quiz_System.enums.Role;
import com.example.Quiz_System.enums.Status;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    QuizTaker toQuizTaker(UserRegisterRequestDto userRegisterRequestDto);

    @AfterMapping
    default void enhanceQuizTaker(@MappingTarget QuizTaker quizTaker) {
        quizTaker.setStatus(Status.PENDING);
        quizTaker.setRole(Role.QuizTaker);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    Admin toAdmin(UserRegisterRequestDto userRegisterRequestDto);

    @AfterMapping
    default void enhanceAdmin(@MappingTarget Admin admin) {
        admin.setRole(Role.ADMIN);
        admin.setStatus(Status.APPROVED);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "quizzes", ignore = true)
    QuizCreator toQuizCreator(UserRegisterRequestDto userRegisterRequestDto);

    @AfterMapping
    default void enhanceQuizCreator(@MappingTarget QuizCreator quizCreator){
        quizCreator.setRole(Role.QuizCreator);
        quizCreator.setStatus(Status.PENDING);
        quizCreator.setQuizzes(new ArrayList<>());
    }
}
