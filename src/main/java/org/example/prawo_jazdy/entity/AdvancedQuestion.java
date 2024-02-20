package org.example.prawo_jazdy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdvancedQuestion implements Question{
    @Id
    @GeneratedValue
    private Long id;
    private int number;
    private String question;
    @ToString.Exclude
    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Answer> answers;


}
