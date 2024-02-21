package org.example.prawo_jazdy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String showHomepage(){
        return "home";
    }
    @GetMapping("/leaderboard")
    public String showLeaderboard(){
        return "leaderboard";
    }

}
