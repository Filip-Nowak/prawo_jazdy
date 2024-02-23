package org.example.prawo_jazdy.controller;

import lombok.RequiredArgsConstructor;
import org.example.prawo_jazdy.entity.Leaderboard;
import org.example.prawo_jazdy.model.LeaderboardModel;
import org.example.prawo_jazdy.model.UserScoreModel;
import org.example.prawo_jazdy.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final QuestionService questionService;
    @GetMapping("/")
    public String showHomepage(){
        return "home";
    }
    @GetMapping("/leaderboard")
    public String showLeaderboard(Model model){
        List<Leaderboard> leaderboard=questionService.getLeaderboard();
        LeaderboardModel leaderboardModel = new LeaderboardModel(new LinkedList<>());
        for(Leaderboard score:leaderboard){

            leaderboardModel.getScores().add(UserScoreModel.builder()
                    .nickname(score.getNickname())
                    .score(score.getScore())
                    .time(score.getTime())
                    .date(score.getDate()).build());
        }
        model.addAttribute("leaderboard",leaderboardModel);
        return "leaderboard";
    }

}
