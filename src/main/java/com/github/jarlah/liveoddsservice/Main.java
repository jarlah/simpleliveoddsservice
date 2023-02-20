package com.github.jarlah.liveoddsservice;

import org.apache.commons.lang3.StringUtils;

class Main {
    public static void main(String[] args) {
        var repo = new ScoreRepositoryMemoryImpl();
        addScore(repo, "Mexico", 0, "Canada", 5);
        addScore(repo, "Spain", 10, "Brazil", 2);
        addScore(repo, "Germany", 2, "France", 2);
        addScore(repo, "Uruguay", 6, "Italy", 6);
        addScore(repo, "Argentina", 3, "Australia", 1);
        System.out.println(StringUtils.join(repo.getAllScores(), "\n"));
    }

    private static void addScore(ScoreRepository scoreRepository, String homeName, int homeScore, String awayName, int awayScore) {
        var homeTeam = new Team(homeName, homeScore);
        var awayTeam = new Team(awayName, awayScore);
        scoreRepository.addScore(homeTeam, awayTeam);
    }
}