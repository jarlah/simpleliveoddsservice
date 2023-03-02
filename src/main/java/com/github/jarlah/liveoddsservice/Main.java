package com.github.jarlah.liveoddsservice;

import com.github.jarlah.liveoddsservice.exceptions.SameTeamAdded;
import org.apache.commons.lang3.StringUtils;

/**
 * This Main class shows the completed solution.
 *
 * @author Jarl André Hübenthal
 */
public class Main {
    public static void main(String[] args) throws SameTeamAdded {
        var repo = new ScoreRepositoryMemoryImpl();
        repo.addScore("Mexico", 0, "Canada", 5);
        repo.addScore("Spain", 10, "Brazil", 2);
        repo.addScore("Germany", 2, "France", 2);
        repo.addScore("Uruguay", 6, "Italy", 6);
        repo.addScore("Argentina", 3, "Australia", 1);
        System.out.println("Score board");
        System.out.println(StringUtils.join(repo.getAllScores(), "\n"));
        System.out.println("Score summary");
        System.out.println(StringUtils.join(repo.getAllScoresSorted(), "\n"));

    }
}