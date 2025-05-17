package com.example.Quiz_System.controller.auth;

import com.example.Quiz_System.dto.UserLoginDto;
import com.example.Quiz_System.dto.UserRegisterRequestDto;
import com.example.Quiz_System.service.auth.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;

    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/quiz-taker/register")
    public ResponseEntity<String> registerQuizTaker(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto){

        return userAuthService.registerQuizTaker(userRegisterRequestDto);
    }

    @PostMapping("/quiz-creator/register")
    public ResponseEntity<String> registerQuizCreator(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto){

        return userAuthService.registerQuizCreator(userRegisterRequestDto);
    }

    @PostMapping("/quiz-taker/login")
    public ResponseEntity<String> loginQuizTaker(@Valid @RequestBody UserLoginDto userLoginDto){

        return userAuthService.loginUser(userLoginDto);
    }

    @PostMapping("/quiz-creator/login")
    public ResponseEntity<String> loginQuizCreator(@Valid @RequestBody UserLoginDto userLoginDto){

        return userAuthService.loginUser(userLoginDto);
    }


}
