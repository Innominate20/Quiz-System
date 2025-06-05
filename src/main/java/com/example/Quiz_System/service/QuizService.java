package com.example.Quiz_System.service;

import com.example.Quiz_System.dto.CreateQuizDto;
import com.example.Quiz_System.dto.QuizResultDto;
import com.example.Quiz_System.dto.QuizReviewDto;
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
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    public ResponseEntity<List<QuizReviewDto>> reviewMyQuizzes(String quizName){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        var userId = userRepository.findByEmail(userEmail).get().getId();

        var quizList = quizRepository.findByquizNameIgnoreCase(quizName);

        if(quizList.isEmpty()){
            throw new QuizNotFoundException("Quiz with the name \""+ quizName+"\" is not found !");
        }

        var quizResponse = quizList.stream()
                .filter(quiz -> quiz.getQuizCreator().getId() == userId)
                .map(quiz -> {
                    var quizReviewObj = quizMapper.toQuizReviewDto(quiz);
                    quizReviewObj.setQuizOwner(quiz.getQuizCreator().getUsername());
                    return quizReviewObj;
                })
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(quizResponse);
    }

    public ResponseEntity<Page<QuizReviewDto>> reviewAllQuizzes(int page, int size, String quizName, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        var pagedQuizzes = quizRepository.findQuizzes(pageable, quizName);

        if (pagedQuizzes.isEmpty()){

            throw new QuizNotFoundException("No quizzes found !");
        }

        List<QuizReviewDto> filteredList = pagedQuizzes.getContent().stream()
                .filter(quiz -> !quiz.getQuizQuestions().isEmpty())
                .map(quizMapper::toQuizReviewDto)
                .toList();

        if (filteredList.isEmpty()){

            throw new QuizNotFoundException("No quizzes found !");
        }

        Page<QuizReviewDto> result = new PageImpl<>(
                filteredList,
                pagedQuizzes.getPageable(),
                filteredList.size()
        );


        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    public ResponseEntity<?> takeQuiz(String quizName, long quizId){

        var quiz = quizRepository.findByquizNameIgnoreCaseAndId(quizName, quizId).orElseThrow(() -> new QuizNotFoundException("Invalid quiz name or id !"));


        var questioList = quiz.getQuizQuestions();

        var quizQuestionsList = questioList.stream()
                .map(quizQuestionMapper::toQuizQuestionResponseDto)
                .toList();

        double duration  = quiz.getDuration();
        var startDate = quiz.getStartDate();
        var expiration = quiz.getExpiration();

        Map<String, Object> quizResponse = new HashMap<>();

        quizResponse.put("QuizName", quizName);
        quizResponse.put("DeadLine", expiration);
        quizResponse.put("TimeLimit", duration);
        quizResponse.put("StartDate", startDate);
        quizResponse.put("Questions", quizQuestionsList);

        return ResponseEntity.status(HttpStatus.OK).body(quizResponse);
    }

    @Transactional
    public ResponseEntity<?> saveQuizResults(List<QuizResultDto> quizResultDtos, String quizName, long id){

        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var quizTaker = (QuizTaker) userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User with the email \""+userEmail+"\" is not found !"));

        var quizTakerId = quizTaker.getId();

        var optionalQuizResult = quizResultRepository.findByQuizTakerAndQuizNameIgnoreCase(quizTaker, quizName);

        if (optionalQuizResult.isPresent()){

            throw new QuizAlreadyTakenException("You have already taken the quiz !");
        }

        int mark = 0;
        Quiz quiz = quizRepository.findByquizNameIgnoreCaseAndId(quizName, id).orElseThrow(() -> new QuizNotFoundException("Quiz withe the name: \""+quizName+"\" does not exist !"));

        var quizQuestions = quiz.getQuizQuestions();

        Map<Long, Integer> quizQuestionIdentifier = new HashMap<>();
        for (int index=0 ;index<quizQuestions.size();index++){

            quizQuestionIdentifier.put(quizQuestions.get(index).getId(),index);
        }

        int numberOfAnsweredQuestions = quizResultDtos.size();
        int numberOfQuestions = quizQuestions.size();

        for (int counter=0;counter<numberOfAnsweredQuestions;counter++){

            var resultId = quizResultDtos.get(counter).getId();
            var questionToCheckId = quizQuestionIdentifier.get(resultId);
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

        long countedDeleted = quizRepository.deleteByexpirationBefore(LocalDate.now());
        log.info("{} quizzes deleted !",countedDeleted);
    }

}
