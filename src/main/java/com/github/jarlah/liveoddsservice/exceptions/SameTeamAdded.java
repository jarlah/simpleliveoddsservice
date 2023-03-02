package com.github.jarlah.liveoddsservice.exceptions;

public class SameTeamAdded extends Exception{
    public SameTeamAdded() {
        super("Cannot add same team twice");
    }
}
