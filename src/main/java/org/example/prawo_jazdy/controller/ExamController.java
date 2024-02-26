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
                    .answers(answers)
                    .image(true).build();
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
        if(formModel.getAnswers()==null){
            resultModel.setCorrectAnswers(0);
        }else{
        for(QuestionModel questionModel:formModel.getQuestions()){
            List<AnswerModel> answers=new LinkedList<>();
            int questionNumber=questionModel.getNumber();
            Question question=questionService.findByNumber(questionNumber);
            if((question.getCorrectAnswer() + "").equals(formModel.getAnswers().get(questionNumber + ""))){
                resultModel.setCorrectAnswers(resultModel.getCorrectAnswers()+1);
            }
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
            int answerNumber=Integer.parseInt(formModel.getAnswers().get(questionNumber+""));
            QuestionModel questionModel1=QuestionModel.builder()
                    .number(questionNumber)
                    .question(question.getQuestion())
                    .userAnswer(answerNumber)
                    .correctAnswer(question.getCorrectAnswer())
                    .answers(answers)
                    .image(true).build();
            if(questionModel1.getUserAnswer()==questionModel1.getCorrectAnswer()){
                resultModel.setCorrectAnswers(resultModel.getCorrectAnswers()+1);
            }
            resultModel.getQuestionList().add(questionModel1);
        }


        }


//
//        if(formModel.getAnswers()==null){
//            resultModel.setCorrectAnswers(0);
//        }else{
//            System.out.println("xdd: "+formModel.getAnswers().size());
//            for(Map.Entry<String,String> entry:formModel.getAnswers().entrySet()){
//                int questionNumber=Integer.parseInt(entry.getKey());
//                int answerNumber=Integer.parseInt(entry.getValue());
//                List<AnswerModel> answers=new LinkedList<>();
//                if(question instanceof BasicQuestion){
//                    answers.add(
//                            AnswerModel.builder()
//                                    .answer("Tak")
//                                    .number(1).build()
//                    );
//                    answers.add(
//                            AnswerModel.builder()
//                                    .answer("Nie")
//                                    .number(0).build()
//                    );
//                }else{
//                    for(Answer answer:((AdvancedQuestion)question).getAnswers()){
//                        answers.add(
//                                AnswerModel.builder()
//                                        .number(answer.getNumber())
//                                        .answer(answer.getAnswer()).build()
//                        );
//                    }
//                }
//                QuestionModel questionModel=QuestionModel.builder()
//                        .number(questionNumber)
//                        .question(question.getQuestion())
//                        .userAnswer(answerNumber)
//                        .correctAnswer(question.getCorrectAnswer())
//                        .answers(answers)
//                        .image(true).build();
//                if(questionModel.getUserAnswer()==questionModel.getCorrectAnswer()){
//                    resultModel.setCorrectAnswers(resultModel.getCorrectAnswers()+1);
//                }
//                resultModel.getQuestionList().add(questionModel);
//            }
//
//        }

        model.addAttribute("result",resultModel);
        return "result";
    }
    @PostMapping("/saveToLeaderboard")
    public String saveToLeaderboard(@ModelAttribute(name = "result") ExamResultModel resultModel){
        questionService.saveToLeaderboard(resultModel);
        return "redirect:/leaderboard";
    }
}
