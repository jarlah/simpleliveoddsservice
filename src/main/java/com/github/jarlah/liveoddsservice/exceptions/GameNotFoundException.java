package com.github.jarlah.liveoddsservice.exceptions;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(Integer id) {
        super("Score was not found: %s".formatted(id));
    }
}
