package org.example.prawo_jazdy.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.enums.QuestionType;
import org.example.prawo_jazdy.repository.AnswerRepository;
import org.example.prawo_jazdy.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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

        return questions;
    }
    private ArrayList<Long> getRandomIndexes(){
        Random random =new Random();
        ArrayList<Long> arr=new ArrayList<>();
        List<Long> availableIndexes=
        for(int i=0;i<20;i++){
            long randomIndex=random.nextLong(1,questionCount+1);
            if(!arr.contains(randomIndex))
                arr.add(randomIndex);
            else
                i--;
        }
    }
}
