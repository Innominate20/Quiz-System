package com.example.Quiz_System.exception;

public class QuizAlreadyTakenException extends RuntimeException{
    public QuizAlreadyTakenException(String message){
        super(message);
    }
}
