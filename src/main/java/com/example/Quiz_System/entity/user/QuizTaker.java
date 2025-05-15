package com.example.Quiz_System.entity.user;

import com.example.Quiz_System.entity.QuizResult;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("QuizTaker")
@Getter
@Setter
@Access(AccessType.PROPERTY)
public class QuizTaker extends User{

    @Access(AccessType.FIELD)
    @OneToMany(mappedBy = "quizTaker")
    private List<QuizResult> quizResults;
}
