package com.github.jarlah.liveoddsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import com.github.jarlah.liveoddsservice.exceptions.GameNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class GameRepositoryTest {

    private GameRepository gameRepository;

    @BeforeEach
    public void init() {
        gameRepository = new GameRepositoryMemoryImpl();
    }

    @Test
    public void addTwoIdenticalTeams() {
        // Given:
        var team1 = new Team("Team", 1);
        var team2 = new Team("Team", 2);

        // When:
        var exception = Assertions.assertThrows(SameTeamAdded.class, () -> {
            gameRepository.addGame(team1, team2);
        });

        // Then:
        assertEquals("Cannot add same team twice: Team", exception.getMessage());
    }

    @Test
    public void addAndUpdateScore() throws GameNotFoundException, SameTeamAdded {
        // Given:
        var brazil = new Team("Brazil", 0);
        var norway = new Team("Norway", 1);

        // When:
        var score1 = gameRepository.addGame(brazil, norway);
        var updatedBrazil = brazil.withScore(brazil.score() + 1);
        var score2 = gameRepository.updateGame(score1.id(), updatedBrazil.score(), norway.score());

        // Then:
        assertScore(score1, brazil, norway, 1, 0, 1);
        assertScore(score2, updatedBrazil, norway, 1, 1, 1);
        assertEquals(1, gameRepository.getAllGames().size());
    }

    @Test
    public void addAndDelete() throws GameNotFoundException, SameTeamAdded {
        // Given:
        var sweden = new Team("Sweden", 2);
        var germany = new Team("Germany", 3);

        // When:
        var score = gameRepository.addGame(sweden, germany);
        gameRepository.deleteGame(score.id());

        // Then:
        assertScoreTeam(score.homeTeam(), 2, sweden);
        assertEquals(Optional.empty(), gameRepository.getGame(score.id()));
        assertEquals(0, gameRepository.getAllGames().size());
    }

    @Test
    public void addMultipleScores() throws SameTeamAdded {
        // Given:
        var sweden = new Team("Sweden", 2);
        var germany = new Team("Germany", 3);
        var brazil = new Team("Brazil", 4);
        var norway = new Team("Norway", 1);

        // When:
        var score1 = addScore(gameRepository, sweden.name(), sweden.score(), germany.name(), germany.score());
        var score2 = addScore(gameRepository, brazil.name(), brazil.score(), norway.name(), norway.score());

        // Then:
        assertScore(score1, sweden, germany, 1, 2, 3);
        assertScore(score2, brazil, norway, 2, 4, 1);
        // Make sure we can get back all the scores in inserted order
        var allScores = gameRepository.getAllGames();
        assertEquals(2, allScores.size());
        assertScore(allScores.get(0), sweden, germany, 1, 2, 3);
        assertScore(allScores.get(1), brazil, norway, 2, 4, 1);
    }

    @Test
    public void returnsSortedScoresAsPerSpecification() throws SameTeamAdded {
        // Given:
        var uruguay = new Team("Uruguay", 6);
        var italy = new Team("Italy", 6);
        var mexico = new Team("Mexico", 0);
        var canada = new Team("Canada", 5);
        var spain = new Team("Spain", 10);
        var brazil = new Team("Brazil", 2);
        var germany = new Team("Germany", 2);
        var france = new Team("France", 2);
        var argentina = new Team("Argentina", 3);
        var australia = new Team("Australia", 1);

        // When:
        gameRepository.addGame(mexico.name(), mexico.score(), canada.name(), canada.score());
        gameRepository.addGame(spain.name(), spain.score(), brazil.name(), brazil.score());
        gameRepository.addGame(germany.name(), germany.score(), france.name(), france.score());
        gameRepository.addGame(uruguay.name(), uruguay.score(), italy.name(), italy.score());
        gameRepository.addGame(argentina.name(), argentina.score(), australia.name(), australia.score());

        // Then:
        List<Game> sortedGames = gameRepository.getAllGamesSorted();
        assertScore(sortedGames.get(0), uruguay, italy, 4, 6, 6);
        assertScore(sortedGames.get(1), spain, brazil, 2, 10, 2);
        assertScore(sortedGames.get(2), mexico, canada, 1, 0, 5);
        assertScore(sortedGames.get(3), argentina, australia, 5, 3, 1);
        assertScore(sortedGames.get(4), germany, france, 3, 2, 2);
    }

    private static Game addScore(GameRepository gameRepository, String homeName, int homeScore, String awayName, int awayScore) throws SameTeamAdded {
        var homeTeam = new Team(homeName, homeScore);
        var awayTeam = new Team(awayName, awayScore);
        return gameRepository.addGame(homeTeam, awayTeam);
    }

    @SuppressWarnings("SameParameterValue")
    private static void assertScore(Game game, Team homeTeam, Team awayTeam, int gameId, int homeTeamScore, int awayTeamScore) {
        assertEquals(game.id(), gameId);
        assertScoreTeam(game.homeTeam(), homeTeamScore, homeTeam);
        assertScoreTeam(game.awayTeam(), awayTeamScore, awayTeam);
    }

    private static void assertScoreTeam(Team scoreTeam, int score, Team team) {
        assertEquals(scoreTeam.score(), score);
        assertEquals(scoreTeam.name(), team.name());
    }
}
