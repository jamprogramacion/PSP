package exercisesUnit2;

import jam.Utils;

/**
 * Thread that sleeps for a random time
 */
class EasyThread extends Thread {
    public EasyThread(String name) {

        super(name);
    }

    @Override
    public void run() {
        final int MIN_SLEEP_TIME = 5;
        final int MAX_SLEEP_TIME = 10;

        super.run();

        long sleepSecs = Utils.randomInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME);
        System.out.println("Thread [" + getName() + "] started. Sleep for [" + sleepSecs +"] seconds");
        try {
            Thread.sleep(sleepSecs * 1000);
        } catch (InterruptedException e) {
            System.out.println("Thread [" + getName() + "] interrupted!!");

            return;
        }

        System.out.println("Thread [" + getName() + "] finished");
    }
}

/**
 * Starts some EasyThreads
 */
public class Exercise1 {
    static final int NUM_THREADS = 5;

    public static void main(String[] args) {
        for (int numThr = 0; numThr < NUM_THREADS; numThr++) {
            EasyThread waitThread = new EasyThread("number " + numThr);
            waitThread.start();
        }
    }
}
