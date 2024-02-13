package org.example.prawo_jazdy.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.enums.QuestionType;
import org.example.prawo_jazdy.repository.AnswerRepository;
import org.example.prawo_jazdy.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private Random random;
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
    public Optional<Question> getRandomBasicQuestion() {
        int randomId = random.nextInt((int) questionRepository.count()) + 1;
        return questionRepository.findQuestionByIdAndQuestionType((long) randomId, QuestionType.TRUE_FALSE);

    }
    public Optional<Question> getRandomSpecializedQuestion(){
        int randomId = random.nextInt((int) questionRepository.count()) + 1;
        return questionRepository.findQuestionByIdAndQuestionType((long) randomId, QuestionType.THREE_ANSWERS);
    }
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }
    public List<Question> getTestQuestions() {
        List<Question>questions=new LinkedList<>();
        for(int i=0;i<20;i++){
            Optional<Question> question=getRandomBasicQuestion();
            question.ifPresent(questions::add);
        }
        for(int i=0;i<12;i++){
            Optional<Question> question=getRandomSpecializedQuestion();
            question.ifPresent(questions::add);
        }
        if(questions.size()<32){
            throw new RuntimeException("Not enough questions in database");
        }
        return questions;
    }
}
