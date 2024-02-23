package org.example.prawo_jazdy.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class QuestionModel {
    private int number;
    private List<AnswerModel> answers;
    private String question;
    private int correctAnswer;
    private int userAnswer;
    private boolean image;
}
