package org.example.prawo_jazdy.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class UserScoreModel {
    private String nickname;
    private int score;
    private String time;
    private LocalDate date;
}
