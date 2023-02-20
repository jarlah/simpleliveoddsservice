package com.github.jarlah.liveoddsservice;

public class Team {
    private final String name;
    private final Integer score;

    public Team(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public Team(Team team, Integer score) {
        this.name = team.name;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
