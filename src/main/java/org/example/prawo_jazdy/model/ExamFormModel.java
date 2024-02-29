package org.example.prawo_jazdy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamFormModel {
    private Map<String, String> answers;
    private List<String> questions;
    private String startDateTime;
}
