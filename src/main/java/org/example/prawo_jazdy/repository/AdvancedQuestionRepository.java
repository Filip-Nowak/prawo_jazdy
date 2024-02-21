package org.example.prawo_jazdy.repository;

import org.example.prawo_jazdy.entity.AdvancedQuestion;
import org.example.prawo_jazdy.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvancedQuestionRepository extends JpaRepository<AdvancedQuestion,Long> {
    Optional<Question> findByNumber(int questionNumber);
}
