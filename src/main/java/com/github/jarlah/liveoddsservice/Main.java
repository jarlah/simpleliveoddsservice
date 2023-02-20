package com.github.jarlah.liveoddsservice;

import org.apache.commons.lang3.StringUtils;

class Main {
    public static void main(String[] args) {
        var scoreRepository = new ScoreRepositoryMemoryImpl();
        var mexico = new Team("Mexico", 0);
        var canada = new Team("Canada", 5);
        scoreRepository.addScore(mexico, canada);
        var spain = new Team("Spain", 10);
        var brazil = new Team("Brazil", 2);
        scoreRepository.addScore(spain, brazil);
        var germany = new Team("Germany", 2);
        var france = new Team("France", 2);
        scoreRepository.addScore(germany, france);
        var uruguay = new Team("Uruguay", 6);
        var italy = new Team("Italy", 6);
        scoreRepository.addScore(uruguay, italy);
        var argentina = new Team("Argentina", 3);
        var australia = new Team("Australia", 1);
        scoreRepository.addScore(argentina, australia);
        System.out.println(StringUtils.join(scoreRepository.getAllScores(), "\n"));

    }
}