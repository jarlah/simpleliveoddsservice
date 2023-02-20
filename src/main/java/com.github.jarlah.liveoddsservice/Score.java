package com.github.jarlah.liveoddsservice;

public class Score {
    private final Integer id;
    private final Integer homeTeam;
    private final Integer awayTeam;

    public Score(Integer id, Integer homeTeam, Integer awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public Integer getAwayTeam() {
        return awayTeam;
    }

    public Integer getId() {
        return id;
    }
}
