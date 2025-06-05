package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.QuizQuestionDto;
import com.example.Quiz_System.dto.UpdateQuestionDto;
import com.example.Quiz_System.entity.QuizQuestion;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.exception.QuizNotFoundException;
import com.example.Quiz_System.exception.QuizOwnershipException;
import com.example.Quiz_System.exception.QuizQuestionNotFoundException;
import com.example.Quiz_System.mapper.QuizQuestionMapper;
import com.example.Quiz_System.repository.QuizQuestionRepository;
import com.example.Quiz_System.repository.QuizRepository;
import com.example.Quiz_System.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuizQuestionService {

    private final QuizRepository quizRepository;
    private final QuizQuestionMapper quizQuestionMapper;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;

    public QuizQuestionService(QuizRepository quizRepository, QuizQuestionMapper quizQuestionMapper, QuizQuestionRepository quizQuestionRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionMapper = quizQuestionMapper;
        this.quizQuestionRepository = quizQuestionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<String> updateQuizQuestions(String quizName, long id, List<UpdateQuestionDto> updateQuestionDtos){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var quiz = quizRepository.findByquizNameIgnoreCaseAndId(quizName, id)
                .orElseThrow(() -> new QuizNotFoundException("Invalid quizName or quizId!"));

        if (!quiz.getQuizCreator().getEmail().equals(userEmail)){

            throw new QuizOwnershipException("You are not the owner of the quiz !");
        }

        var questions = quiz.getQuizQuestions();

        if (questions.isEmpty()) {
            throw new QuizQuestionNotFoundException("Invalid question ids or questions are not added!");
        }

        Map<Long, QuizQuestion> questionMap = questions.stream()
                .collect(Collectors.toMap(QuizQuestion::getId, Function.identity()));

        for (var updateDto : updateQuestionDtos) {

            var question = questionMap.get(updateDto.getId());

            if (question != null) {
                quizQuestionMapper.updateQuestionFromDto(updateDto, question);
            }
        }

        quizQuestionRepository.saveAll(questions);

        return ResponseEntity.ok("Questions updated successfully.");

    }

    @Transactional
    public ResponseEntity<?> addQuestionsToQuiz(List<QuizQuestionDto>list, String quizName,long quizId){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var user = (QuizCreator) userRepository.findByEmail(userEmail).get(); // If the user is authenticated then it definitely exists

        var quiz = quizRepository.findByquizNameIgnoreCaseAndId(quizName, quizId).orElseThrow(() -> new QuizNotFoundException("Invalid quiz name or id !"));

        if (quiz.getQuizCreator().getId() != user.getId()){
            throw new QuizOwnershipException("You are not the owner of the quiz !");
        }


        var quizObjectList =list.stream().map(quizQuestionMapper::toQuizQuestion)
                .peek(quizObj -> quizObj.setQuiz(quiz))
                .toList();

        quizQuestionRepository.saveAll(quizObjectList);

        return ResponseEntity.status(HttpStatus.CREATED).body("Questions added to the quiz !");
    }
}
