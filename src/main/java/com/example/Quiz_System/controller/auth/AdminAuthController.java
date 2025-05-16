package com.example.Quiz_System.controller.auth;

import com.example.Quiz_System.dto.UserLoginDto;
import com.example.Quiz_System.dto.UserRegisterRequestDto;
import com.example.Quiz_System.service.auth.UserAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminAuthController {

    private final UserAuthService userAuthService;

    public AdminAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegisterRequestDto userRegisterRequestDto){

        return userAuthService.registerAdmin(userRegisterRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody UserLoginDto userLoginDto){

        return userAuthService.loginUser(userLoginDto);
    }
}
