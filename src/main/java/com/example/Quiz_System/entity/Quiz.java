package com.example.Quiz_System.entity;

import com.example.Quiz_System.entity.user.QuizCreator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "quiz_name")
    private String quizName;
    private LocalDateTime deadline;
    private double duration;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<QuizQuestion> quizQuestions;
    @ManyToOne
    @JoinColumn(name = "quizCreator_id")
    private QuizCreator quizCreator;
}
