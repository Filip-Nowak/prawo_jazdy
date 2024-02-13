package org.example.prawo_jazdy.controller;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.Question;
import org.example.prawo_jazdy.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @GetMapping
    public String showHomeView() {
        return "test/home";
    }
    @GetMapping("/test")
    public String showTestView(Model model) {
        List<Question> questions = questionService.getTestQuestions();
        model.addAttribute("questions", questions);
        return "test/test";
    }
}
