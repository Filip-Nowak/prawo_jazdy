package org.example.prawo_jazdy.repository;

import org.example.prawo_jazdy.entity.BasicQuestion;
import org.example.prawo_jazdy.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasicQuestionRepository extends JpaRepository<BasicQuestion, Long> {
    Optional<Question> findByNumber(int questionNumber);
}
