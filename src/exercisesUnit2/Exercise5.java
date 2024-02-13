package exercisesUnit2;

class InterruptCounter implements Runnable {
    private static final int SLEEP_TIME = 1000;
    private final String name;
    private final int maxInterruptions;
    private int numMessage = 0;
    private int numInterruption = 0;

    public InterruptCounter(String name, int maxInterruptions) {
        this.name = name;
        this.maxInterruptions = maxInterruptions;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(SLEEP_TIME);
                numMessage++;
                System.out.println("Thread [" + name + "] showing message number " + numMessage);
            } catch (InterruptedException e) {
                numInterruption++;
                System.out.println("Thread [" + name + "] interruption number " + numInterruption);

                if (numInterruption == maxInterruptions) {
                    return;
                }
            }
        }
    }
}
public class Exercise5 {
    private static final int SLEEP_TIME = 1000;
    static final int NUM_THREADS = 5;
    static final int NUM_INTERRUPTIONS = 10;
    private static final Thread[] interruptCounters = new Thread[NUM_THREADS];

    public static void main(String[] args) {
        for (int numInt = 0; numInt < interruptCounters.length; numInt++) {
            interruptCounters[numInt] = new Thread(new InterruptCounter("number " + numInt, NUM_INTERRUPTIONS));
            interruptCounters[numInt].start();
        }

        int countersActive = interruptCounters.length;
        while (countersActive > 0) {
            for (Thread interruptCounter : interruptCounters) {
                if (interruptCounter.isAlive()) {
                    interruptCounter.interrupt();
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException ignore) {

                    }
                } else {
                    countersActive--;
                }
            }
        }
    }
}
