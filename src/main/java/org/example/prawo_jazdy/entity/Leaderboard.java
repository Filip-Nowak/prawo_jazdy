package org.example.prawo_jazdy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nickname;
    private int score;
    private int time;
    private LocalDate date;
}
