package com.github.jarlah.liveoddsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ScoreRepositoryTest {

    private ScoreRepository scoreRepository;

    @BeforeEach
    public void init() {
        scoreRepository = new ScoreRepositoryMemoryImpl();
    }

    @Test
    public void addAndUpdateScore() throws ScoreNotFoundException {
        // Given:
        var brazil = new Team("Brazil", 0);
        var norway = new Team("Norway", 1);

        // When:
        var score1 = scoreRepository.addScore(brazil, norway);
        var updatedBrazil = new Team(brazil.name(), 1);
        var score2 = scoreRepository.updateScore(score1.id(), updatedBrazil, norway);

        // Then:
        testScoreUpdated(score1, brazil, norway, 1, 0, 1);
        testScoreUpdated(score2, updatedBrazil, norway, 1, 1, 1);
    }

    @Test
    public void addAndDelete() throws ScoreNotFoundException {
        // Given:
        var sweden = new Team("Sweden", 2);
        var germany = new Team("Germany", 3);

        // When:
        var score = scoreRepository.addScore(sweden, germany);
        assertScoreTeam(score.homeTeam(), 2, sweden);
        scoreRepository.deleteScore(score.id());
        assertEquals(Optional.empty(), scoreRepository.getStore(score.id()));
    }

    @SuppressWarnings("SameParameterValue")
    private static void testScoreUpdated(Score score, Team homeTeam, Team awayTeam, int scoreId, int homeTeamScore, int awayTeamScore) {
        assertEquals(score.id(), scoreId);
        assertScoreTeam(score.homeTeam(), homeTeamScore, homeTeam);
        assertScoreTeam(score.awayTeam(), awayTeamScore, awayTeam);
    }

    private static void assertScoreTeam(Team scoreTeam, int score, Team team) {
        assertEquals(scoreTeam.score(), score);
        assertEquals(scoreTeam.name(), team.name());
    }
}
