package org.example.prawo_jazdy.service;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.AdvancedQuestion;
import org.example.prawo_jazdy.entity.BasicQuestion;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.repository.AdvancedQuestionRepository;
import org.example.prawo_jazdy.repository.AnswerRepository;
import org.example.prawo_jazdy.repository.BasicQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final BasicQuestionRepository basicQuestionRepository;
    private final AdvancedQuestionRepository advancedQuestionRepository;
    private final AnswerRepository answerRepository;
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

}
