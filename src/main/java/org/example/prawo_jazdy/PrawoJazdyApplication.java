package org.example.prawo_jazdy;

import org.example.prawo_jazdy.entity.AdvancedQuestion;
import org.example.prawo_jazdy.entity.Answer;
import org.example.prawo_jazdy.entity.BasicQuestion;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class PrawoJazdyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrawoJazdyApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(QuestionService questionService){
        return runner ->{
            List<Question> questions=readBasicQuestions();
            for(Question question:questions){

                questionService.saveQuestion(question);
            }
            List<Question> questions1=readAdvancedQuestions();
            for(Question question:questions1){
                questionService.saveQuestion(question);
            }
            List<Question> test=questionService.getTestQuestions();
            System.out.println(test);
        };
    }
    private List<Question> readBasicQuestions(){
        System.out.println("xdd");
        Resource resource=new ClassPathResource("files/questions/basic_questions.txt");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] array = bufferedReader.lines().collect(Collectors.joining()).split(";;");
        List<Question> questionList=new LinkedList<>();
        for(String questionStr:array){
            String[] strings = questionStr.split("#");
            int number=Integer.parseInt(strings[0]);
            String quest=strings[1].split("-")[0];
            boolean correct;
            correct= strings[1].split("-")[1].equals("T");
            BasicQuestion question=BasicQuestion.builder()
                    .number(number)
                    .correct(correct)
                    .question(quest)
                    .build();
            questionList.add(question);
        }
        return questionList;
    }
    public List<Question> readAdvancedQuestions(){
        Resource resource=new ClassPathResource("files/questions/advanced_questions.txt");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] array = bufferedReader.lines().collect(Collectors.joining()).split(";;");
        List<Question> questionList=new LinkedList<>();
        for(String questionStr:array){
            String[] strings = questionStr.split("#");
            int number=Integer.parseInt(strings[0]);
            String quest=strings[1].split("@")[0];
            String[] answersStr=strings[1].split("@")[1].split(";");
            List<Answer> answers=new LinkedList<>();
            int i=1;
            int correct=1;
            AdvancedQuestion question=AdvancedQuestion.builder()
                    .question(quest)
                    .number(number)
                    .build();
            for(String answerStr:answersStr){
                char[] chars=answerStr.toCharArray();
                if(chars[chars.length-1]=='_'){
                    correct=i;
                    answerStr=removeLastChar(answerStr);
                }
                Answer answer = Answer.builder()
                        .answer(answerStr)
                        .question(question)
                        .number(i)
                        .build();
                answers.add(answer);
                i++;
            }
            question.setCorrectAnswer(correct);
            question.setAnswers(answers);
            questionList.add(question);
        }
        return questionList;
    }
    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }
}
