package exercisesUnit22;

import java.util.concurrent.Semaphore;

class Singer extends Thread {
    private final Semaphore micro;

    public Singer(int singerNumber, Semaphore micro) {
        super(String.valueOf(singerNumber));

        this.micro = micro;
    }

    @Override
    public void run() {
        final int SING_TIME = 3;
        final int REST_TIME = 5;

        super.run();

        while (true) {
            try {
                if (micro.tryAcquire(1)) {
                    System.out.println("Singer [" + getName() + "] start singing...");
                    Thread.sleep(SING_TIME * 1000L);
                    micro.release(1);
                }
                System.out.println("Singer [" + getName() + "] start rest...");
                Thread.sleep(REST_TIME * 1000L);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
public class Exercise4 {
    public static void main(String[] args) {
        final int NUM_SINGERS = 5;
        final Semaphore micro = new Semaphore(2);

        for (int singerNum = 0; singerNum < NUM_SINGERS; singerNum++) {
            Singer singer = new Singer(singerNum, micro);
            singer.start();
        }
    }
}
