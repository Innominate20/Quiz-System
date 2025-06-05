package com.example.Quiz_System.exception;

public class QuizQuestionNotFoundException extends RuntimeException{
    public QuizQuestionNotFoundException(String message){
        super(message);
    }
}
