package com.github.jarlah.liveoddsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;
import org.junit.jupiter.api.Test;

public class ScoreRepositoryTest {

    @Test
    public void addAndUpdateScore() throws ScoreNotFoundException {
        // Given:
        var scoreRepository = new ScoreRepositoryMemoryImpl();
        var brazil = new Team("Brazil", 0);
        var norway = new Team("Norway", 1);

        // When:
        var score1 = scoreRepository.addScore(brazil, norway);
        var updatedBrazil = new Team(brazil, 1);
        var score2 = scoreRepository.updateScore(score1.getId(), updatedBrazil, norway);

        // Then:
        testScoreUpdated(score1, brazil, norway, 1, 0, 1);
        testScoreUpdated(score2, updatedBrazil, norway, 1, 1, 1);
    }

    @SuppressWarnings("SameParameterValue")
    private static void testScoreUpdated(Score score, Team homeTeam, Team awayTeam, int scoreId, int homeTeamScore, int awayTeamScore) {
        assertEquals(score.getId(), scoreId);

        assertEquals(score.getHomeTeam().getScore(), homeTeamScore);
        assertEquals(score.getHomeTeam().getName(), homeTeam.getName());
        assertEquals(score.getHomeTeam().getScore(), homeTeam.getScore());

        assertEquals(score.getAwayTeam().getScore(), awayTeamScore);
        assertEquals(score.getAwayTeam().getName(), awayTeam.getName());
        assertEquals(score.getAwayTeam().getScore(), awayTeam.getScore());
    }
}
