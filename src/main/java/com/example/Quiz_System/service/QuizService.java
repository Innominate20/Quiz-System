package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizResultDto;
import com.example.Quiz_System.entity.Quiz;
import com.example.Quiz_System.entity.QuizResult;
import com.example.Quiz_System.entity.user.QuizCreator;
import com.example.Quiz_System.entity.user.QuizTaker;
import com.example.Quiz_System.exception.QuizAlreadyTakenException;
import com.example.Quiz_System.exception.QuizNotFoundException;
import com.example.Quiz_System.exception.UserNotFoundException;
import com.example.Quiz_System.mapper.QuizMapper;
import com.example.Quiz_System.mapper.QuizQuestionMapper;
import com.example.Quiz_System.repository.QuizQuestionRepository;
import com.example.Quiz_System.repository.QuizRepository;
import com.example.Quiz_System.repository.QuizResultRepository;
import com.example.Quiz_System.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class QuizService {

    private final QuizResultRepository quizResultRepository;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMapper quizQuestionMapper;
    private final UserRepository userRepository;

    public QuizService(QuizResultRepository quizResultRepository, QuizRepository quizRepository, QuizMapper quizMapper, QuizQuestionRepository quizQuestionRepository, QuizQuestionMapper quizQuestionMapper, UserRepository userRepository) {
        this.quizResultRepository = quizResultRepository;
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMapper = quizQuestionMapper;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createQuiz(CreateQuizDto createQuizDto){

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var quizCreator = (QuizCreator) userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with the email \""+userEmail+"\" is not found !"));

        Quiz quiz = quizMapper.toQuiz(createQuizDto);

        quiz.setQuizCreator(quizCreator);

        quizRepository.save(quiz);

        return ResponseEntity.status(HttpStatus.CREATED).body("Quiz created !");
    }



    public ResponseEntity<?> takeQuiz(String name){

        var quiz = quizRepository.findByquizNameIgnoreCase(name).orElseThrow(() -> new QuizNotFoundException("Quiz with the name \""+ name+"\" is not found !"));

        var questioList = quiz.getQuizQuestions();

        var quizQuestionsList = questioList.stream()
                .map(quizQuestionMapper::toQuizQuestionResponseDto)
                .toList();

        double duration  = quiz.getDuration();
        var startDate = quiz.getStartDate();
        var deadLine = quiz.getDeadline();

        Map<String, Object> quizResponse = new HashMap<>();

        quizResponse.put("DeadLine", deadLine);
        quizResponse.put("TimeLimit", duration);
        quizResponse.put("StartDate", startDate);
        quizResponse.put("Questions", quizQuestionsList);

        return ResponseEntity.status(HttpStatus.OK).body(quizResponse);
    }

    public ResponseEntity<?> saveQuizResults(List<QuizResultDto> quizResultDtos, String quizName){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var quizTaker = (QuizTaker) userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with the email \""+userEmail+"\" is not found !"));

        var quizTakerId = quizTaker.getId();

        var optionalQuizResult = quizResultRepository.findByQuizTakerAndQuizTaker(quizTakerId, quizName);

        if (optionalQuizResult.isPresent()){

            throw new QuizAlreadyTakenException("You have already taken the quiz !");
        }

        int mark = 0;
        Quiz quiz = quizRepository.findByquizNameIgnoreCase(quizName).orElseThrow(() -> new QuizNotFoundException("Quiz withe the name: \""+quizName+"\" does not exist !"));

        var quizQuestions = quiz.getQuizQuestions();

        Map<Long, Integer> quizQuestionIdentifier = new HashMap<>();
        for (int index=0 ;index<quizQuestions.size();index++){

            quizQuestionIdentifier.put(quizQuestions.get(index).getId(),index);
        }

        int numberOfAnsweredQuestions = quizResultDtos.size();
        int numberOfQuestions = quizQuestions.size();

        for (int counter=0;counter<numberOfAnsweredQuestions;counter++){

            var id = quizResultDtos.get(counter).getId();
            var questionToCheckId = quizQuestionIdentifier.get(id);
            var questionToCheck = quizQuestions.get(questionToCheckId);

            if (quizResultDtos.get(counter).getCorrectAnswer() == questionToCheck.getCorrectAnswer()){
                mark++;
            }
        }

        int percentage = (int) (((double) mark/numberOfQuestions) * 100);

        QuizResult quizResult = QuizResult.builder()
                .quizTaker(quizTaker)
                .mark(mark)
                .quizName(quizName)
                .build();

        quizResultRepository.save(quizResult);

        Map<String, Integer> response = new HashMap<>();
        response.put("Mark",mark);
        response.put("Percentage",percentage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteExpiredQuizzes(){

        long countedDeleted = quizRepository.deleteBydeadlineBefore(LocalDate.now());
        log.info("{} quizzes deleted !",countedDeleted);
    }

}
