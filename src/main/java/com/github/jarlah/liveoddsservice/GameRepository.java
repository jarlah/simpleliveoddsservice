package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import com.github.jarlah.liveoddsservice.exceptions.GameNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The score repository interface.
 * Simple version with no futures.
 * Will probably need refactoring if a database implementation is to be added.
 *
 * @author Jarl André Hübenthal
 */
public interface GameRepository {
    Game addGame(Team homeTeam, Team awayTeam) throws SameTeamAdded;
    Game updateGame(Integer gameId, Integer homeTeam, Integer awayTeam) throws GameNotFoundException;
    Game deleteGame(Integer gameId) throws GameNotFoundException;
    Optional<Game> getGame(Integer gameId);
    List<Game> getAllGames();
    List<Game> getAllGamesSorted();

    default void addGame(String homeName, int homeScore, String awayName, int awayScore) throws SameTeamAdded {
        var homeTeam = new Team(homeName, homeScore);
        var awayTeam = new Team(awayName, awayScore);
        this.addGame(homeTeam, awayTeam);
    }
}
