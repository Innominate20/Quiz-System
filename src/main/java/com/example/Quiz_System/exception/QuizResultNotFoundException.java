package com.example.Quiz_System.exception;

import com.example.Quiz_System.entity.QuizResult;

public class QuizResultNotFoundException extends RuntimeException{
    public QuizResultNotFoundException(String message){
        super(message);
    }
}
