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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
