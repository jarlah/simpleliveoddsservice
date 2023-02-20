# Simple Live Odds Service Score Board

A simple Java 19 based cli app with unit tests to facilitate managing and displaying a score board.

The scoreboard supports the following operations:
1. [x] Start a new game, assuming initial score 0 – 0 and adding it the scoreboard.
   This should capture following parameters:
   a. Home team
   b. Away team
2. [x] Update score. This should receive a pair of absolute scores: home team score and away
   team score.
3. [x] Finish game currently in progress. This removes a match from the scoreboard.
4. [x] Get a summary of games in progress ordered by their total score. The games with the same
   total score will be returned ordered by the most recently started match in the scoreboard. 

## How to run

1. Install Java 19 and Maven
2. Run `mvn compile exec:java -Dexec.mainClass="com.github.jarlah.liveoddsservice.Main"`
3. Should display started games in order started, then sorted as per specification
4. Run tests `mvn verify`