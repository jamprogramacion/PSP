package exercisesUnit2;

import jam.Utils;

class EasyThread extends Thread {
    public EasyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();

        long sleepSecs = Utils.randomInt(5, 10);
        System.out.println("Thread [" + this.getName() + "] started. Sleep for [" + sleepSecs +"] seconds");
        try {
            Thread.sleep(sleepSecs * 1000);
        } catch (InterruptedException e) {
            System.out.println("Thread [" + this.getName() + "] interrupted!!");

            return;
        }

        System.out.println("Thread [" + this.getName() + "] finished");
    }
}
public class Exercise1 {
    static final int NUM_THREADS = 5;

    public static void main(String[] args) {
        for (int numThr = 0; numThr < NUM_THREADS; numThr++) {
            EasyThread waitThread = new EasyThread("number " + numThr);
            waitThread.start();
        }
    }
}
