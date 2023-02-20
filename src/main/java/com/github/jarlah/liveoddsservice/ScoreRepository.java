package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The score repository interface.
 * Simple version with no futures.
 *
 * @author Jarl André Hübenthal
 */
public interface ScoreRepository {
    Score addScore(Team homeTeam, Team awayTeam);
    Score updateScore(Integer scoreId, Team homeTeam, Team awayTeam) throws ScoreNotFoundException;
    Score deleteScore(Integer scoreId) throws ScoreNotFoundException;
    Optional<Score> getStore(Integer scoreId);
    List<Score> getAllScores();
    List<Score> getAllScoresSorted();
}
