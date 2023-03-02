package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The score repository interface.
 * Simple version with no futures.
 * Will probably need refactoring if a database implementation is to be added.
 *
 * @author Jarl André Hübenthal
 */
public interface ScoreRepository {
    Score addScore(Team homeTeam, Team awayTeam) throws SameTeamAdded;
    Score updateScore(Integer scoreId, Team homeTeam, Team awayTeam) throws ScoreNotFoundException;
    Score deleteScore(Integer scoreId) throws ScoreNotFoundException;
    Optional<Score> getStore(Integer scoreId);
    List<Score> getAllScores();
    List<Score> getAllScoresSorted();

    default void addScore(String homeName, int homeScore, String awayName, int awayScore) throws SameTeamAdded {
        var homeTeam = new Team(homeName, homeScore);
        var awayTeam = new Team(awayName, awayScore);
        this.addScore(homeTeam, awayTeam);
    }
}
