# Simple Live Odds Service Score Board

A simple Java 19 based cli app with unit tests to facilitate managing and displaying a score board.

The scoreboard supports the following operations:
1. [x] Start a new game, assuming initial score 0 – 0 and adding it the scoreboard.
   This should capture following parameters: Home team, Away team 
   - Solved: Starting new game is same as creating a score in the score board repository
2. [x] Update score. This should receive a pair of absolute scores: home team score and away
   team score. 
   - Solved: Updating game takes in immutable teams with new (or unchanged) scores
3. [x] Finish game currently in progress. This removes a match from the scoreboard.
   - Solved: Score is deleted
4. [x] Get a summary of games in progress ordered by their total score. The games with the same
   total score will be returned ordered by the most recently started match in the scoreboard. 
   - Solved: Collections sorting.

## How to run

1. Install Java 19 and Maven
2. Run `mvn compile exec:java -Dexec.mainClass="com.github.jarlah.liveoddsservice.Main"`
3. Should display started games in order started, then sorted as per specification
4. Run tests `mvn verify`

## About commit history and TDD(test first)

I have not started make tests for simple stuff like models and in memory collection manipulation.

But when i started on the actual import bit in the task, which is the sorted score board, i made test first and then fixed the test.

In addition, the git commit history is not carefully crafted. It is what it is and what I typed into the commit message on command line. In most cases, where you have a big feature, you will have a lot of intermediate commits that doesn't necessarily make sense for anyone else than you. Then you squash these commits and make sense of the commits. Or just add a description in PR and squash the PR when its merged. But mostly I use git for what its worth, doing small intermediate commits and using rebasing a lot. I could have prefixed them with chore: or test: maybe, but it doesn't matter for such small piece of code like this.