package org.example.prawo_jazdy.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExamResultModel {
    private int correctAnswers;
    List<QuestionModel> questionList;
    private String nickname;
}
