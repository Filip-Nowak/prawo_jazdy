package org.example.prawo_jazdy.repository;

import org.example.prawo_jazdy.entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Integer> {
    List<Leaderboard> findAllByOrderByScoreDescTimeAsc();
}
