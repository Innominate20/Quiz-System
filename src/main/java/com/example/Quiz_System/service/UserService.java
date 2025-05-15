package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.UserRequestDto;
import com.example.Quiz_System.entity.user.Admin;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.entity.user.QuizTaker;
import com.example.Quiz_System.mapper.UserMapper;
import com.example.Quiz_System.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> createQuizTaker(UserRequestDto userRequestDto){

        QuizTaker quizTaker = userMapper.toQuizTaker(userRequestDto);
        quizTaker.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userRepository.save(quizTaker);

        return ResponseEntity.status(HttpStatus.OK).body("QuizTaker created !");
    }

    public ResponseEntity<String> createAdmin(UserRequestDto userRequestDto){

        Admin admin = userMapper.toAdmin(userRequestDto);
        admin.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created !");
    }

    public ResponseEntity<?> createQuizCreator(UserRequestDto userRequestDto){

        QuizCreator quizCreator = userMapper.toQuizCreator(userRequestDto);

        quizCreator.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        userRepository.save(quizCreator);

        return ResponseEntity.status(HttpStatus.CREATED).body("QuizCreator created !");
    }
}
