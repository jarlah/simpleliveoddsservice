package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.ScoreNotFoundException;

class Main {
    public static void main(String[] args) throws ScoreNotFoundException {
        var scoreRepository = new ScoreRepositoryMemoryImpl();
        var brazil = new Team("Brazil", 0);
        var norway = new Team("Norway", 1);
        var score1 = scoreRepository.addScore(brazil, norway);
        System.out.println(score1);
        var score2 = scoreRepository.updateScore(score1.id(), new Team(brazil.name(), 1), norway);
        System.out.println(score2);
        scoreRepository.getStore(score2.id());
        var deletedScore = scoreRepository.deleteScore(score2.id());
        System.out.println(deletedScore);
        if (scoreRepository.getStore(score2.id()).isPresent()) {
            throw new RuntimeException();
        }
        if (scoreRepository.getAllScores().size() > 0) {
            throw new RuntimeException();
        }
    }
}