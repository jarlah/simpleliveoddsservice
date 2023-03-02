package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import com.github.jarlah.liveoddsservice.exceptions.GameNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A memory implementation of the {@link GameRepository}
 *
 * @author Jarl André Hübenthal
 */
public class GameRepositoryMemoryImpl implements GameRepository {
    private final AtomicInteger atomicId;
    private final LinkedList<Game> games;

    public GameRepositoryMemoryImpl() {
        this.games = new LinkedList<>();
        this.atomicId = new AtomicInteger(0);
    }

    @Override
    public Game addGame(Team homeTeam, Team awayTeam) throws SameTeamAdded {
        if (homeTeam.equals(awayTeam)) {
            throw new SameTeamAdded(homeTeam);
        }
        var nextId = this.atomicId.addAndGet(1);
        var score = new Game(nextId, homeTeam, awayTeam);
        this.games.add(score);
        return score;
    }

    /**
     * In a real world scenario, with a database, I would have used a transaction,
     * optimally using SELECT FOR UPDATE pattern, in for ex Postgres.
     * But this is in-memory and NOT memory safe, and method is therefore synchronized.
     * Synchronizing methods like this is not a good pattern, but will work for now.
     *
     * @param gameId  The id of the score to update
     * @param homeTeam the updated score of the home team
     * @param awayTeam the updated score of the away team
     * @return the updated score
     * @throws GameNotFoundException exception is thrown if gameId is not found
     */
    @Override
    public synchronized Game updateGame(Integer gameId, Integer homeTeam, Integer awayTeam) throws GameNotFoundException {
        var scoreToUpdate = unsafeGetGame(gameId);
        var newScore = new Game(gameId, scoreToUpdate.homeTeam().withScore(homeTeam), scoreToUpdate.awayTeam().withScore(awayTeam));
        var scoreIndex = this.games.indexOf(scoreToUpdate);
        this.games.set(scoreIndex, newScore);
        return newScore;
    }

    /**
     * Removing an item from scores linked list can be done without synchronization,
     * because if it doesn't exist, it will not throw.
     *
     * @param gameId the id of the score to delete
     * @return The delete score
     * @throws GameNotFoundException exception is thrown if gameId is not found
     */
    @Override
    public Game deleteGame(Integer gameId) throws GameNotFoundException {
        var scoreToDelete = unsafeGetGame(gameId);
        // Returned boolean from remove is ignored, so this call is idempotent
        this.games.remove(scoreToDelete);
        return scoreToDelete;
    }

    @Override
    public Optional<Game> getGame(Integer gameId) {
        return getMaybeGame(gameId);
    }

    @Override
    public List<Game> getAllGames() {
        return new LinkedList<>(this.games);
    }

    @Override
    public List<Game> getAllGamesSorted() {
        var toBeSorted = new LinkedList<>(this.games);
        toBeSorted.sort(
                Comparator.comparing(Game::totalScore, Comparator.reverseOrder())
                        .thenComparing(Game::id, Comparator.reverseOrder())
        );
        return toBeSorted;
    }

    /**
     * Gets the score by id.
     * This will throw exception if called with id that doesnt exist.
     *
     * @param gameId the id of the score to get
     * @return The score
     * @throws GameNotFoundException throws if not found
     */
    private Game unsafeGetGame(Integer gameId) throws GameNotFoundException {
        return getMaybeGame(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @NotNull
    private Optional<Game> getMaybeGame(Integer gameId) {
        return this.games.stream()
                .filter(game -> game.id().equals(gameId))
                .findFirst();
    }
}
