package com.github.jarlah.liveoddsservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MainTest {

    private final Main main = new Main();

    @Test
    void addition() {
        assertEquals(2, main.add(1, 1));
    }

}