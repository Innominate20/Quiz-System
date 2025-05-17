package com.example.Quiz_System.controller;

import com.example.Quiz_System.dto.UserRegisterRequestDto;
import com.example.Quiz_System.service.auth.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserAuthService userAuthService;

    public AdminController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }


}
