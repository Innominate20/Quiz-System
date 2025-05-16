package com.example.Quiz_System.service.auth;

import com.example.Quiz_System.dto.UserLoginDto;
import com.example.Quiz_System.dto.UserRegisterRequestDto;
import com.example.Quiz_System.entity.user.Admin;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.entity.user.QuizTaker;
import com.example.Quiz_System.exception.UserAlreadyExistsException;
import com.example.Quiz_System.jwt.JwtUtil;
import com.example.Quiz_System.mapper.UserMapper;
import com.example.Quiz_System.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserAuthService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public ResponseEntity<String> registerQuizTaker(UserRegisterRequestDto userRegisterRequestDto){

        var userOptional = userRepository.findByEmail(userRegisterRequestDto.getEmail());

        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with the email \""+ userRegisterRequestDto.getEmail()+"\" already exists !");
        }

        QuizTaker quizTaker = userMapper.toQuizTaker(userRegisterRequestDto);
        quizTaker.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));

        userRepository.save(quizTaker);

        return ResponseEntity.status(HttpStatus.OK).body("QuizTaker created !");
    }

    @Transactional
    public ResponseEntity<String> registerAdmin(UserRegisterRequestDto userRegisterRequestDto){

        var userOptional = userRepository.findByEmail(userRegisterRequestDto.getEmail());

        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with the email \""+ userRegisterRequestDto.getEmail()+"\" already exists !");
        }
        Admin admin = userMapper.toAdmin(userRegisterRequestDto);
        admin.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));

        userRepository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered !");
    }

    @Transactional
    public ResponseEntity<String> registerQuizCreator(UserRegisterRequestDto userRegisterRequestDto){

        var userOptional = userRepository.findByEmail(userRegisterRequestDto.getEmail());

        if (userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with the email \""+ userRegisterRequestDto.getEmail()+"\" already exists !");
        }
        QuizCreator quizCreator = userMapper.toQuizCreator(userRegisterRequestDto);

        quizCreator.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));

        userRepository.save(quizCreator);

        return ResponseEntity.status(HttpStatus.CREATED).body("QuizCreator created !");
    }

    public ResponseEntity<String> loginUser(UserLoginDto userLoginDto){

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToke(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


}
