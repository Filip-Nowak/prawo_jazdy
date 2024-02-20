package org.example.prawo_jazdy.repository;

import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findQuestionByIdAndQuestionType(Long id, QuestionType questionType);
}
