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
        var repo = new GameRepositoryMemoryImpl();
        repo.addGame("Mexico", 0, "Canada", 5);
        repo.addGame("Spain", 10, "Brazil", 2);
        repo.addGame("Germany", 2, "France", 2);
        repo.addGame("Uruguay", 6, "Italy", 6);
        repo.addGame("Argentina", 3, "Australia", 1);
        System.out.println("Score board");
        System.out.println(StringUtils.join(repo.getAllGames(), "\n"));
        System.out.println("Score summary");
        System.out.println(StringUtils.join(repo.getAllGamesSorted(), "\n"));

    }
}