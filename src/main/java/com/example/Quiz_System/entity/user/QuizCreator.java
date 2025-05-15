package com.example.Quiz_System.entity.user;

import com.example.Quiz_System.entity.Quiz;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("QuizCreator")
@Setter
@Getter
@Access(AccessType.PROPERTY)
public class QuizCreator extends User {

    @Access(AccessType.FIELD)
    @OneToMany(mappedBy = "quizCreator")
    private List<Quiz> quizzes;
}
