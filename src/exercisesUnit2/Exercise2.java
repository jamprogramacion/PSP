package exercisesUnit2;

import jam.Utils;

/**
 * Takes account of total race time
 */
class RaceTime {
    private static long raceTime = 0;

    public static long getRaceTime() {
        return raceTime;
    }

    /**
     * @param time_secs Adds time to race time.
     */
    public static void addRaceTime(long time_secs) {
        raceTime += time_secs;
    }
}

/**
 * Thread representing a runner, that runs for a random time.
 */
class RunnersThread extends Thread {

    /**
     * @param bibNumber Bib number to identify the runner
     */
    public RunnersThread(int bibNumber) {
        super(String.valueOf(bibNumber));
    }

    @Override
    public void run() {
        final int MIN_RUN_TIME = 5;
        final int MAX_RUN_TIME = 10;

        super.run();

        long raceSecs = Utils.randomInt(MIN_RUN_TIME, MAX_RUN_TIME);
        System.out.println("Runner [" + this.getName() + "] started. Running for [" + raceSecs +"] seconds");
        try {
            Thread.sleep(raceSecs * 1000);
        } catch (InterruptedException e) {
            System.out.println("Runner [" + this.getName() + "] interrupted!!");
        }

        RaceTime.addRaceTime(raceSecs);
        System.out.println("Runner [" + this.getName() + "] finished");
    }
}

/**
 * Starts some runners.
 * One runner cannot start to run until previous runner has finished.
 */
public class Exercise2 {
    private static final int NUM_RUNNERS = 4;
    public static void main(String[] args) {
        RunnersThread[] runnerThread = new RunnersThread[NUM_RUNNERS];

        for (int numBib = 0; numBib < runnerThread.length; numBib++) {
            runnerThread[numBib] = new RunnersThread(numBib);
            runnerThread[numBib].start();
            try {
                runnerThread[numBib].join();
            } catch (InterruptedException e) {
                System.out.println("Runner [" + numBib + "] interrupted!!");
            }
        }

        System.out.println("Total time of race: " + RaceTime.getRaceTime() + " seconds");
    }
}
