package com.github.jarlah.liveoddsservice;

import org.jetbrains.annotations.NotNull;

/**
 * The score model.
 * Represents an ongoing match between two teams.
 *
 * @author Jarl André Hübenthal
 */
public record Game(Integer id, Team homeTeam, Team awayTeam) {
    public @NotNull Integer totalScore() {
        return homeTeam.score() + awayTeam.score();
    }
}
