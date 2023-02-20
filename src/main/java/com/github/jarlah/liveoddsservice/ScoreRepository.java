package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

/**
 * The score repository interface.
 * Simple version with no futures.
 *
 * @author Jarl André Hübenthal
 */
public interface ScoreRepository {
    Score addScore(Integer homeTeam, Integer awayTeam);
    Score updateScore(Integer scoreId, Integer homeTeam, Integer awayTeam) throws ScoreNotFoundException;
    Score deleteScore(Integer scoreId) throws ScoreNotFoundException;
}
