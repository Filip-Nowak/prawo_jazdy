package org.example.prawo_jazdy.service;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.AdvancedQuestion;
import org.example.prawo_jazdy.entity.BasicQuestion;
import org.example.prawo_jazdy.entity.Leaderboard;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.model.ExamResultModel;
import org.example.prawo_jazdy.repository.AdvancedQuestionRepository;
import org.example.prawo_jazdy.repository.AnswerRepository;
import org.example.prawo_jazdy.repository.BasicQuestionRepository;
import org.example.prawo_jazdy.repository.LeaderboardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final BasicQuestionRepository basicQuestionRepository;
    private final AdvancedQuestionRepository advancedQuestionRepository;
    private final LeaderboardRepository leaderboardRepository;
    private Random random;
    public Question saveQuestion(Question question){
        if(question instanceof BasicQuestion)
            return basicQuestionRepository.save((BasicQuestion) question);
        else
            return advancedQuestionRepository.save((AdvancedQuestion) question);
    }
    public List<Question> getTestQuestions() {
        List<Question>questions=new LinkedList<>();
        long basicQuestionCount=basicQuestionRepository.count();
        long advancedQuestionCount=advancedQuestionRepository.count();
        List<Long> basicIndexes=getRandomIndexes(20,basicQuestionCount);
        List<Long> advancedIndexes=getRandomIndexes(12,advancedQuestionCount);
        for(Long index:basicIndexes){
            questions.add(basicQuestionRepository.findById(index).orElseThrow());
        }
        for(Long index:advancedIndexes){
            questions.add(advancedQuestionRepository.findById(index).orElseThrow());
        }
        return questions;
    }
    private List<Long>getRandomIndexes(int size,long max){
        Random random=new Random();
        List<Long>indexes=new ArrayList<>();
        while(indexes.size()<size){
            long randomIndex=random.nextLong(1,max+1);
            if(!indexes.contains(randomIndex))
                indexes.add(randomIndex);
        }
        return indexes;
    }

    public Question findByNumber(int questionNumber) {
//        return basicQuestionRepository.findByNumber(questionNumber).orElse(advancedQuestionRepository.findByNumber(questionNumber).orElseThrow());
        System.out.println("Question number: " + questionNumber);
        Optional<Question> question = basicQuestionRepository.findByNumber(questionNumber);
        if (question.isPresent()) {
            System.out.println("Basic question found");
            return question.get();
        } else {
            System.out.println("Advanced question found");
            return advancedQuestionRepository.findByNumber(questionNumber).orElseThrow();
        }
    }

    public void saveToLeaderboard(ExamResultModel resultModel) {
        Leaderboard leaderboard = Leaderboard.builder()
                .score(resultModel.getCorrectAnswers())
                .nickname(resultModel.getNickname())
                .date(LocalDate.now())
                .build();
        leaderboardRepository.save(leaderboard);
    }

    public List<Leaderboard> getLeaderboard() {
        return leaderboardRepository.findAllByOrderByScoreDescTimeDesc();
    }
}
