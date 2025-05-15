package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.UserRequestDto;
import com.example.Quiz_System.repository.UserRepository;
import com.example.Quiz_System.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    public ResponseEntity<String> createNewAdmin(@RequestBody UserRequestDto userRequestDto){

        return userService.createAdmin(userRequestDto);
    }
}
