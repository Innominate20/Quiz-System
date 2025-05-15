package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.UserRequestDto;
import com.example.Quiz_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/quiz-taker")
    public ResponseEntity<?> createNewQuizTaker(@Valid @RequestBody UserRequestDto userRequestDto){

        return userService.createQuizTaker(userRequestDto);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> createNewAdmin(@Valid @RequestBody UserRequestDto userRequestDto){

        return userService.createAdmin(userRequestDto);
    }

    @PostMapping("/register/quiz-creator")
    public ResponseEntity<?> createNewQuizCreator(@Valid @RequestBody UserRequestDto userRequestDto){

        return userService.createQuizCreator(userRequestDto);
    }

}
