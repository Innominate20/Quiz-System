package com.example.Quiz_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableScheduling
@SpringBootApplication
public class QuizSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizSystemApplication.class, args);
	}

}
