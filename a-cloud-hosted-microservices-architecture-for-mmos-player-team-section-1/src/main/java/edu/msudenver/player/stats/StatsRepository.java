package edu.msudenver.player.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    @Query("SELECT stats FROM Stats stats WHERE stats.playerId = ?1")
    List<Stats> findByPlayerId(Long playerId);
}