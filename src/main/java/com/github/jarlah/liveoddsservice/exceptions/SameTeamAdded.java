package com.github.jarlah.liveoddsservice.exceptions;

import com.github.jarlah.liveoddsservice.Team;

public class SameTeamAdded extends Exception{
    public SameTeamAdded(Team team) {
        super("Cannot add same team twice: %s".formatted(team.name()));
    }
}
