package org.example.prawo_jazdy.controller;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.AdvancedQuestion;
import org.example.prawo_jazdy.entity.Answer;
import org.example.prawo_jazdy.entity.BasicQuestion;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.model.AnswerModel;
import org.example.prawo_jazdy.model.ExamFormModel;
import org.example.prawo_jazdy.model.ExamResultModel;
import org.example.prawo_jazdy.model.QuestionModel;
import org.example.prawo_jazdy.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ExamController {
    private final QuestionService questionService;
    @PostMapping("/exam")
    public String startExam(Model model){
        List<Question> questionList=questionService.getTestQuestions();
        List<QuestionModel> models=new LinkedList<>();
        for(Question question:questionList){
            LinkedList<AnswerModel>answers=new LinkedList<>();
            if(question instanceof BasicQuestion){
                answers.add(
                        AnswerModel.builder()
                                .answer("Tak")
                                .number(1).build()
                );
                answers.add(
                        AnswerModel.builder()
                                .answer("Nie")
                                .number(0).build()
                );
            }else{
                for(Answer answer:((AdvancedQuestion)question).getAnswers()){
                    answers.add(
                            AnswerModel.builder()
                                    .number(answer.getNumber())
                                    .answer(answer.getAnswer()).build()
                    );
                }
            }
            QuestionModel questionModel=QuestionModel.builder()
                    .question(question.getQuestion())
                    .number(question.getNumber())
                    .answers(answers).build();
            System.out.println(questionModel);
            models.add(questionModel);
        }
        model.addAttribute("formData",new ExamFormModel());
        System.out.println("chuj"+models.size()+" "+questionList.size());
        model.addAttribute("questionList",models);
        return "exam";
    }
    @PostMapping("/processForm")
    public String processExam(Model model, @ModelAttribute(name = "formData") ExamFormModel formModel){
        ExamResultModel resultModel=ExamResultModel.builder()
                .questionList(new LinkedList<>())
                .correctAnswers(0).build();
        for(Map.Entry<String,String> entry:formModel.getAnswers().entrySet()){
            int questionNumber=Integer.parseInt(entry.getKey());
            int answerNumber=Integer.parseInt(entry.getValue());
            Question question=questionService.findByNumber(questionNumber);
            List<AnswerModel> answers=new LinkedList<>();
            if(question instanceof BasicQuestion){
                answers.add(
                        AnswerModel.builder()
                                .answer("Tak")
                                .number(1).build()
                );
                answers.add(
                        AnswerModel.builder()
                                .answer("Nie")
                                .number(0).build()
                );
            }else{
                for(Answer answer:((AdvancedQuestion)question).getAnswers()){
                    answers.add(
                            AnswerModel.builder()
                                    .number(answer.getNumber())
                                    .answer(answer.getAnswer()).build()
                    );
                }
            }
            QuestionModel questionModel=QuestionModel.builder()
                    .number(questionNumber)
                    .question(question.getQuestion())
                    .userAnswer(answerNumber)
                    .correctAnswer(question.getCorrectAnswer())
                    .answers(answers).build();
            if(questionModel.getUserAnswer()==questionModel.getCorrectAnswer()){
                resultModel.setCorrectAnswers(resultModel.getCorrectAnswers()+1);
            }
            resultModel.getQuestionList().add(questionModel);
        }
        model.addAttribute("result",resultModel);
        return "result";
    }
}
