package org.example.prawo_jazdy.controller;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.service.QuestionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @GetMapping("/test")
    public String showTestView(Model model) {
        model.addAttribute("imageNumber",1);
        return "test/test";
    }

}
