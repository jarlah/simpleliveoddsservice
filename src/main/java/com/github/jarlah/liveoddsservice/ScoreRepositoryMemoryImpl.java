package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A memory implementation of the {@link ScoreRepository}
 *
 * @author Jarl André Hübenthal
 */
public class ScoreRepositoryMemoryImpl implements ScoreRepository {
    private final AtomicInteger atomicId;
    private final LinkedList<Score> scores;

    public ScoreRepositoryMemoryImpl() {
        this.scores = new LinkedList<>();
        this.atomicId = new AtomicInteger(0);
    }

    @Override
    public Score addScore(Team homeTeam, Team awayTeam) throws SameTeamAdded {
        if (homeTeam.equals(awayTeam)) {
            throw new SameTeamAdded(homeTeam);
        }
        var nextId = this.atomicId.addAndGet(1);
        var score = new Score(nextId, homeTeam, awayTeam);
        this.scores.add(score);
        return score;
    }

    /**
     * In a real world scenario, with a database, I would have used a transaction,
     * optimally using SELECT FOR UPDATE pattern, in for ex Postgres.
     * But this is in-memory and NOT memory safe, and method is therefore synchronized.
     * Synchronizing methods like this is not a good pattern, but will work for now.
     *
     * @param scoreId  The id of the score to update
     * @param homeTeam the updated score of the home team
     * @param awayTeam the updated score of the away team
     * @return the updated score
     * @throws ScoreNotFoundException exception is thrown if scoreId is not found
     */
    @Override
    public synchronized Score updateScore(Integer scoreId, Integer homeTeam, Integer awayTeam) throws ScoreNotFoundException {
        var scoreToUpdate = unsafeGetScore(scoreId);
        var newScore = new Score(scoreId, scoreToUpdate.homeTeam().withScore(homeTeam), scoreToUpdate.awayTeam().withScore(awayTeam));
        var scoreIndex = this.scores.indexOf(scoreToUpdate);
        this.scores.set(scoreIndex, newScore);
        return newScore;
    }

    /**
     * Removing an item from scores linked list can be done without synchronization,
     * because if it doesn't exist, it will not throw.
     *
     * @param scoreId the id of the score to delete
     * @return The delete score
     * @throws ScoreNotFoundException exception is thrown if scoreId is not found
     */
    @Override
    public Score deleteScore(Integer scoreId) throws ScoreNotFoundException {
        var scoreToDelete = unsafeGetScore(scoreId);
        // Returned boolean from remove is ignored, so this call is idempotent
        this.scores.remove(scoreToDelete);
        return scoreToDelete;
    }

    @Override
    public Optional<Score> getStore(Integer scoreId) {
        return getMaybeScore(scoreId);
    }

    @Override
    public List<Score> getAllScores() {
        return new LinkedList<>(this.scores);
    }

    @Override
    public List<Score> getAllScoresSorted() {
        var toBeSorted = new LinkedList<>(this.scores);
        toBeSorted.sort(
                Comparator.comparing(Score::totalScore, Comparator.reverseOrder())
                        .thenComparing(Score::id, Comparator.reverseOrder())
        );
        return toBeSorted;
    }

    /**
     * Gets the score by id.
     * This will throw exception if called with id that doesnt exist.
     *
     * @param scoreId the id of the score to get
     * @return The score
     * @throws ScoreNotFoundException throws if not found
     */
    private Score unsafeGetScore(Integer scoreId) throws ScoreNotFoundException {
        return getMaybeScore(scoreId)
                .orElseThrow(() -> new ScoreNotFoundException(scoreId));
    }

    @NotNull
    private Optional<Score> getMaybeScore(Integer scoreId) {
        return this.scores.stream()
                .filter(score -> score.id().equals(scoreId))
                .findFirst();
    }
}
