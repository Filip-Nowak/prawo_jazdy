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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ExamController {
    private final QuestionService questionService;

    @PostMapping("/exam")
    public String startExam(Model model) {
        List<Question> questionList = questionService.getTestQuestions();
        List<QuestionModel> models = new LinkedList<>();
        for (Question question : questionList) {
            LinkedList<AnswerModel> answers = new LinkedList<>();
            if (question instanceof BasicQuestion) {
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
            } else {
                for (Answer answer : ((AdvancedQuestion) question).getAnswers()) {
                    answers.add(
                            AnswerModel.builder()
                                    .number(answer.getNumber())
                                    .answer(answer.getAnswer()).build()
                    );
                }
            }
            QuestionModel questionModel = QuestionModel.builder()
                    .question(question.getQuestion())
                    .number(question.getNumber())
                    .answers(answers)
                    .image(true).build();
            System.out.println(questionModel);
            models.add(questionModel);
        }
        ExamFormModel formModel=new ExamFormModel();

        formModel.setStartDateTime(LocalDateTime.now().toString());
        model.addAttribute("formData", formModel);
        System.out.println("chuj" + models.size() + " " + questionList.size());
        model.addAttribute("questionList", models);
        return "exam";
    }

    @PostMapping("/processForm")
    public String processExam(Model model, @ModelAttribute(name = "formData") ExamFormModel formModel) {
        System.out.println(formModel);
        System.out.println("endTime: "+LocalDateTime.now());
        LocalDateTime endDateTime=LocalDateTime.now();
        ExamResultModel resultModel = ExamResultModel.builder()
                .questionList(new LinkedList<>())
                .correctAnswers(0).build();
        System.out.println(formModel.getAnswers());
        if(formModel.getAnswers()==null){
            List<QuestionModel> models=new LinkedList<>();
            for(String questionString:formModel.getQuestions()){
                int questionNumber = Integer.parseInt(questionString);
                Question question=questionService.findByNumber(questionNumber);
                LinkedList<AnswerModel> answers = new LinkedList<>();
                if (question instanceof BasicQuestion) {
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
                } else {
                    for (Answer answer : ((AdvancedQuestion) question).getAnswers()) {
                        answers.add(
                                AnswerModel.builder()
                                        .number(answer.getNumber())
                                        .answer(answer.getAnswer()).build()
                        );
                    }
                }
                QuestionModel questionModel=QuestionModel.builder()
                        .image(true)
                        .userAnswer(-1)
                        .number(questionNumber)
                        .question(question.getQuestion())
                        .correctAnswer(question.getCorrectAnswer())
                        .answers(answers)
                        .build();
                models.add(questionModel);
            }
            ExamResultModel examResultModel=new ExamResultModel();
            examResultModel.setQuestionList(models);
            examResultModel.setCorrectAnswers(0);
            String startDateTimeStr= formModel.getStartDateTime();
            LocalDateTime startDateTime=LocalDateTime.parse(startDateTimeStr,DateTimeFormatter.ISO_DATE_TIME);
            long seconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime);
            String time;
            if(seconds%60<10) {
                time= seconds/60+":0"+seconds%60;
            }
            else {
                time= seconds/60+":"+seconds%60;
            }
            examResultModel.setTime(time);
            examResultModel.setPassed(false);
            model.addAttribute("result",examResultModel);
            return "result";
        }


            for (String questionString : formModel.getQuestions()) {
                List<AnswerModel> answers = new LinkedList<>();
                int questionNumber = Integer.parseInt(questionString);
                Question question = questionService.findByNumber(questionNumber);
                if(formModel.getAnswers().get(questionNumber + "") == null){

                    if (question instanceof BasicQuestion) {
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
                    } else {
                        for (Answer answer : ((AdvancedQuestion) question).getAnswers()) {
                            answers.add(
                                    AnswerModel.builder()
                                            .number(answer.getNumber())
                                            .answer(answer.getAnswer()).build()
                            );
                        }
                    }


                    QuestionModel questionModel1 = QuestionModel.builder()
                            .number(questionNumber)
                            .question(question.getQuestion())
                            .correctAnswer(question.getCorrectAnswer())
                            .answers(answers)
                            .userAnswer(-1)
                            .image(true).build();
                    resultModel.getQuestionList().add(questionModel1);

                    continue;
                }
                if ((question.getCorrectAnswer() + "").equals(formModel.getAnswers().get(questionNumber + ""))) {
                    resultModel.setCorrectAnswers(resultModel.getCorrectAnswers() + 1);
                }
                if (question instanceof BasicQuestion) {
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
                } else {
                    for (Answer answer : ((AdvancedQuestion) question).getAnswers()) {
                        answers.add(
                                AnswerModel.builder()
                                        .number(answer.getNumber())
                                        .answer(answer.getAnswer()).build()
                        );
                    }
                }
                int answerNumber = Integer.parseInt(formModel.getAnswers().get(questionNumber + ""));
                QuestionModel questionModel1 = QuestionModel.builder()
                        .number(questionNumber)
                        .question(question.getQuestion())
                        .userAnswer(answerNumber)
                        .correctAnswer(question.getCorrectAnswer())
                        .answers(answers)
                        .image(true).build();
                resultModel.getQuestionList().add(questionModel1);
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
        String startDateTimeStr= formModel.getStartDateTime();
        //DateTimeFormatter formatter=DateTimeFormatter.ofPattern();
        LocalDateTime startDateTime=LocalDateTime.parse(startDateTimeStr,DateTimeFormatter.ISO_DATE_TIME);
        long seconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime);
        String time;
        if(seconds%60<10)
            time= seconds/60+":0"+seconds%60;
        else
            time= seconds/60+":"+seconds%60;
        resultModel.setTime(time);
        if(resultModel.getCorrectAnswers()>30)
            resultModel.setPassed(true);
        else
            resultModel.setPassed(false);
        model.addAttribute("result", resultModel);
        return "result";
    }

    @PostMapping("/saveToLeaderboard")
    public String saveToLeaderboard(@ModelAttribute(name = "result") ExamResultModel resultModel) {
        questionService.saveToLeaderboard(resultModel);
        return "redirect:/leaderboard";
    }
}
