package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

class Main {
    public static void main(String[] args) throws ScoreNotFoundException {
        ScoreRepository scoreRepository = new ScoreRepositoryMemoryImpl();
        Score score1 = scoreRepository.addScore(new Team("Brazil", 0), new Team("Norway", 1));
        System.out.println(scoreRepository.updateScore(score1.getId(), null, null));
    }

    public int add(int i, int i1) {
        return i + i1;
    }
}