package com.example.Quiz_System.exception;

public class QuizNotFoundException extends RuntimeException{

    public QuizNotFoundException(String message){
        super(message);
    }
}
