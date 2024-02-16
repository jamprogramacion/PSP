package exercisesUnit22;

import jam.Utils;
import java.util.concurrent.Semaphore;

class Fountain {
    private final Semaphore tap = new Semaphore(1);
    private long water = 50000;

    public void drink(Pupil pupil, long time) throws InterruptedException {
        if (water == 0) {
            throw new InterruptedException("Pupil [" + pupil.getName() + "] cannot drink. EMPTY FOUNTAIN!!");
        }

        tap.acquire();
        long timeCanDrink = Math.min(time, water);
        if (water > 0) {
            if (time == timeCanDrink) {
                System.out.println("Pupil [" + pupil.getName() + "] drinking for " + (timeCanDrink / 1000) + "s");
            } else {
                System.out.println("Pupil [" + pupil.getName() + "] only can drink for " + (timeCanDrink / 1000) + "s of " +
                        (time / 1000) + "s requested");
            }
            water -= timeCanDrink;
            System.out.println("Remaining water: " + water + "l");
            try {
                Thread.sleep(time);
            } finally {
                tap.release();
            }
        } else {
            tap.release();

            throw new InterruptedException("Pupil [" + pupil.getName() + "] try to drink after waiting, but... EMPTY FOUNTAIN!!");
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
            int queue = fountain.queueLength();
            if (queue <= MAX_QUEUE_LENGTH) {
                System.out.println("Pupil [" + getName() + "] in queue...");
                try {
                    fountain.drink(this, Utils.randomInt(MIN_DRINK_TIME, MAX_DRINK_TIME) * 1000L);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());

                    return;
                }
            } else {
                try {
                    System.out.println("Queue too long [" + queue + "], pupil [" + getName() + "] starts studying...");
                    Thread.sleep(STUDY_TIME * 1000L);
                } catch (InterruptedException e) {
                    return;
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
    }
}
