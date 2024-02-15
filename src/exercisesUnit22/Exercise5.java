package exercisesUnit22;

import jam.Utils;
import java.util.concurrent.Semaphore;

class Fountain {
    private final Semaphore tap = new Semaphore(1);
    private long water = 100000;

    public long getWater() {
        return water;
    }

    public void drink(String pupilName, long time) {
        try {
            tap.acquire();
            System.out.println("Pupil [" + pupilName + "] drinking for " + (time / 1000) + "s");
            Thread.sleep(time);
            water -= time;
            System.out.println("Remaining water: " + water + "l");
            tap.release();
        } catch (InterruptedException ignore) {

        }
    }

    public int queueLength() {
        return tap.getQueueLength();
    }
}

class Pupil extends Thread {
    private final Fountain fountain;

    public Pupil(int pupilNumber, Fountain fountain) {
        super(String.valueOf(pupilNumber));

        this.fountain = fountain;
    }

    @Override
    public void run() {
        final int MAX_QUEUE_LENGTH = 5;
        final int STUDY_TIME = 10;
        final int MIN_DRINK_TIME = 5;
        final int MAX_DRINK_TIME = 10;
        super.run();

        while (true) {
            if (fountain.queueLength() <= MAX_QUEUE_LENGTH) {
                System.out.println("Pupil [" + getName() + "] in queue...");
                fountain.drink(getName(), Utils.randomInt(MIN_DRINK_TIME, MAX_DRINK_TIME) * 1000L);
            } else {
                try {
                    System.out.println("Queue too long, pupil [" + getName() + "] starts studying...");
                    Thread.sleep(STUDY_TIME * 1000L);
                } catch (InterruptedException ignore) {

                }
            }
        }
    }
}
public class Exercise5 {
    public static void main(String[] args) {
        final int NUM_PUPILS = 10;
        final Fountain fountain = new Fountain();

        for (int pupilNum = 0; pupilNum < NUM_PUPILS; pupilNum++) {
            Pupil pupil = new Pupil(pupilNum, fountain);
            pupil.start();
        }

        while (fountain.getWater() > 0);
    }
}
