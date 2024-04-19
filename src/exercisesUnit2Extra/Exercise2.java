package exercisesUnit2Extra;

import jam.Utils;

import java.util.Arrays;

/**
 * Thread to simulate a tennis match with a given time.
 */
class TennisMatch extends Thread {
    private long matchTime;

    /**
     * @param matchName Name of the match to show in console.
     * @param matchTime Match time in seconds.
     */
    public TennisMatch(String matchName, long matchTime) {
        super(matchName);

        this.matchTime = matchTime;
    }

    @Override
    public void run() {
        super.run();

        System.out.println(getName() + " started...");
        try {
            sleep(matchTime * 1000);
            System.out.println(getName() + " finished in " + matchTime + " seconds.");
        } catch (InterruptedException e) {
            System.out.println("Match interrupted!!");
        }
    }
}

/**
 * Creates tournament matches, and simulates playing them.
 */
class TennisTournament {
    private static final int MIN_MATCH_TIME = 5;
    private static final int MAX_MATCH_TIME = 10;
    private int tournamentTime;

    /**
     * Plays the match with a random time. When match finish, increments tournament time.
     *
     * @param playerOne Player one name.
     * @param playerTwo player two name.
     *
     * @throws InterruptedException
     */
    private void playMatch(String playerOne, String playerTwo) throws InterruptedException {
        int matchTime = Utils.randomInt(MIN_MATCH_TIME, MAX_MATCH_TIME);
        TennisMatch match = new TennisMatch("Match: " + playerOne + " vs " + playerTwo, matchTime);
        match.start();
        match.join();
        tournamentTime += matchTime;
    }

    /**
     * Creates random matches between players, and plays them.
     *
     * @param players Names of the players on the tournament. It has to be a pair number of players.
     */
    public void runTournament(String[] players) {
        int[] matchList = new int[players.length];

        Arrays.fill(matchList, -1);
        for (int numPlayer = 0; numPlayer < players.length; numPlayer++) {
            int numMatch = Utils.randomInt(0, 100) % players.length;
            while (matchList[numMatch] != -1) {
                numMatch = Utils.randomInt(0, 100) % players.length;
            }
            matchList[numMatch] = numPlayer;
        }

        for (int numMatch = 0; numMatch < (matchList.length / 2); numMatch++) {
            try {
                playMatch(players[matchList[numMatch * 2]], players[matchList[numMatch * 2 + 1]]);
            } catch (InterruptedException e) {
                System.out.println("Match interrupted!!");
            }
        }
    }

    /**
     * @return Total tournament time at the moment.
     */
    public int getTournamentTime() {
        return tournamentTime;
    }
}

public class Exercise2 {
    public static void main(String[] args) {
        final String[] players = {
                "John McEnroe", "Manolo Santana", "Guillermo Vilas", "Carlos Alcaraz",
                "Conchita Martínez", "Gabriela Sabatini", "Steffi Graf", "Paula Badosa"
        };
        TennisTournament tournament = new TennisTournament();

        tournament.runTournament(players);

        System.out.println("Tournament total duration: " + tournament.getTournamentTime());
    }
}