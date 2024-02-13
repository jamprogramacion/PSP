package exercisesUnit2;

import jam.Utils;

class RaceTime {
    private static long raceTime = 0;

    public static long getRaceTime() {

        return raceTime;
    }

    public static void addRaceTime(long time_secs) {

        raceTime += time_secs;
    }
}
class RunnersThread extends Thread {

    public RunnersThread(int bibNumber) {
        super(String.valueOf(bibNumber));
    }

    @Override
    public void run() {
        super.run();

        long raceSecs = Utils.randomInt(5, 10);
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
