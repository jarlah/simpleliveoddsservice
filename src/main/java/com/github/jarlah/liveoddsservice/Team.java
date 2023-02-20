package com.github.jarlah.liveoddsservice;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The team model.
 * Represents a team by their name and their score.
 *
 * @author Jarl André Hübenthal
 */
public record Team(String name, Integer score) {
    @Contract("_ -> new")
    public @NotNull Team withScore(Integer newScore) {
        return new Team(name, newScore);
    }
}
