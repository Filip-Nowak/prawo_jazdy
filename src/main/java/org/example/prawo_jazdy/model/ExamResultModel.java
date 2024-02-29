package org.example.prawo_jazdy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultModel {
    private int correctAnswers;
    List<QuestionModel> questionList;
    private String nickname;
    private String time;
    private boolean passed;
}
