package exercisesUnit22;

import jam.Utils;

/**
 * Manages waiting for runners and start from the judge.
 */
class RaceManager {
    /**
     * @param runner Runner bib.
     */
    public synchronized void RunnerOnStartLine(String runner) {
        System.out.println("Runner [" + runner + "] waiting on start line...");
        try {
            wait();
        } catch (InterruptedException ignore) {

        }
    }

    /**
     * @param lineJudge Line judge name.
     */
    public synchronized void RaceStart(String lineJudge) {
        System.out.println("Line judge [" + lineJudge + "] starts race.");
        notifyAll();
    }
}

/**
 * Runners race for time between MIN_RUN_TIME and MAX_RUN_TIME.
 */
class Runner extends Thread {
    private final RaceManager raceManager;

    /**
     * @param bibNumber Runner bib.
     * @param raceManager race manager.
     */
    public Runner(int bibNumber, RaceManager raceManager) {
        super(String.valueOf(bibNumber));

        this.raceManager = raceManager;
    }

    @Override
    public void run() {
        final int MIN_RUN_TIME = 3;
        final int MAX_RUN_TIME = 5;
        final long RUN_TIME = Utils.randomInt(MIN_RUN_TIME, MAX_RUN_TIME);

        super.run();

        while (true) {
            try {
                raceManager.RunnerOnStartLine(getName());
                System.out.println("Runner [" + getName() + "] running for " + RUN_TIME + "s");
                Thread.sleep(RUN_TIME * 1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * Line judge starts a race every SLEEP_TIME.
 */
class LineJudge extends Thread {
    private final RaceManager raceManager;

    /**
     * @param raceManager Race manager.
     */
    public LineJudge(RaceManager raceManager) {
        super("Evaristo");

        this.raceManager = raceManager;
    }

    @Override
    public void run() {
        final int SLEEP_TIME = 10;

        super.run();

        while (true) {
            try {
                System.out.println("Judge [" + getName() + "] in start line...");
                Thread.sleep(SLEEP_TIME * 1000);
                raceManager.RaceStart(getName());
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * Starts runners and line judge.
 */
public class Exercise6 {
    public static void main(String[] args) {
        final int NUM_RUNNERS = 8;
        final RaceManager raceManager = new RaceManager();
        LineJudge lineJudge = new LineJudge(raceManager);

        for (int numRunner = 1; numRunner <= NUM_RUNNERS; numRunner++) {
            Runner runner = new Runner(numRunner, raceManager);
            runner.start();
        }
        lineJudge.start();
    }
}
