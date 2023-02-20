package com.github.jarlah.liveoddsservice;

/**
 * The score model.
 * TODO Maybe use lombok to auto generate getters and setters?
 *
 * @author Jarl André Hübenthal
 */
public class Score {
    private final Integer id;
    private final Team homeTeam;
    private final Team awayTeam;

    public Score(Integer id, Team homeTeam, Team awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Integer getId() {
        return id;
    }
}
