package com.github.jarlah.liveoddsservice.exceptions;

public class ScoreNotFoundException extends Exception {
    public ScoreNotFoundException(Integer id) {
        super("Score was not found: " + id);
    }
}
