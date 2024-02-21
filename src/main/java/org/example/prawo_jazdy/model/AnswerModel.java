package org.example.prawo_jazdy.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerModel {
    private String answer;
    private int number;
}
