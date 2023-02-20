package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreRepositoryMemoryImpl implements ScoreRepository {
    private AtomicInteger atomicId;
    private LinkedList<Score> scores;

    public ScoreRepositoryMemoryImpl() {
        this.scores = new LinkedList<>();
        this.atomicId = new AtomicInteger(1);
    }

    public ScoreRepositoryMemoryImpl(LinkedList<Score> scores) {
        this.scores = scores;
    }

    @Override
    public Score addScore(Integer homeTeam, Integer awayTeam) {
        Score score = new Score(this.atomicId.addAndGet(1), homeTeam, awayTeam);
        this.scores.add(score);
        return score;
    }

    /**
     * In a real world scenario, with a database, i would have used a transaction,
     * optimally using SELECT FOR READ pattern, in for ex Postgres.
     * But this is in-memory and NOT memory safe, and method is therefore synchronized.
     * Synchronizing methods like this is not a good pattern, but will work for now.
     *
     * @param scoreId The id of the score to updat
     * @param homeTeam the updated score of the home team
     * @param awayTeam the updated score of the away team
     * @return the updated score
     *
     * @throws ScoreNotFoundException exception is thrown if scoreId is not found
     */
    @Override
    public synchronized Score updateScore(Integer scoreId, Integer homeTeam, Integer awayTeam) throws ScoreNotFoundException {
        Score scoreToUpdate = getScore(scoreId);
        Score newScore = new Score(scoreId, homeTeam, awayTeam);
        this.scores.set(this.scores.indexOf(scoreToUpdate), newScore);
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
        Score scoreToDelete = getScore(scoreId);
        this.scores.remove(scoreToDelete);
        return scoreToDelete;
    }

    private Score getScore(Integer scoreId) throws ScoreNotFoundException {
        Optional<Score> maybeScore = this.scores.stream()
                .filter(score -> score.getId().equals(scoreId))
                .findFirst();
        if (maybeScore.isEmpty()) {
            throw new ScoreNotFoundException(scoreId);
        }
        return maybeScore.get();
    }
}
