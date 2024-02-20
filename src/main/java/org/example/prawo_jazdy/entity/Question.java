package org.example.prawo_jazdy.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.prawo_jazdy.enums.QuestionType;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int number;
    private String question;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "question")
    private List<Answer> answers;
    private int correctAnswer;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
}
