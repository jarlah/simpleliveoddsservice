package com.github.jarlah.liveoddsservice;

/**
 * The score model.
 * TODO Maybe use lombok to auto generate getters and setters?
 *
 * @author Jarl André Hübenthal
 */
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
