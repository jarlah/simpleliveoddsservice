package com.github.jarlah.liveoddsservice;

import org.jetbrains.annotations.NotNull;

public class Team {
    private final String name;
    private final Integer score;

    public Team(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public Team(@NotNull Team team, Integer newScore) {
        this(team.name, newScore);
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Team[name='%s',score=%d]".formatted(name, score);
    }
}
