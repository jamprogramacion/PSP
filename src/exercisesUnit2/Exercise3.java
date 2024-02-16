package exercisesUnit2;

import jam.Utils;

/**
 * A simple counter
 */
class Counter {
    private long counter = 0;

    /**
     * @param add Adds to counter
     */
    public synchronized void addCounter(long add) {
        counter += add;
    }

    public long getCounter() {
        return counter;
    }
}

/**
 * Thread that sums one to counter on every iteration, until limit is reached.
 */
class SumCounter extends Thread {
    private final Counter counterSum;
    private long counter = 1;
    private final long limit;

    /**
     * @param name Thread id.
     * @param counterSum Reference to Counter object.
     * @param limit Max value for counter.
     */
    public SumCounter(String name, Counter counterSum, long limit) {
        super(name);

        this.counterSum = counterSum;
        this.limit = limit;
    }

    @Override
    public void run() {
        super.run();

        System.out.println("Thread [" + this.getName() + "] started...");
        while (counter <= limit) {
            counterSum.addCounter(1);
            if ((counter % (int)(limit / 10)) == 0) {
                System.out.println("Thread [" + this.getName() + "] counter = " + (int)(100 * counter / limit) + "%");
            }
            counter++;
        }
        System.out.println("Thread [" + this.getName() + "] finished.");
    }
}

/**
 * Starts threads to sum to counter until limit.
 */
public class Exercise3 {
    private static final int NUM_THREADS = 10;
    private static final long LIMIT = 100000;
    private static final Counter counterSum = new Counter();

    public static void main(String[] args) {
        SumCounter[] sumCounters = new SumCounter[NUM_THREADS];

        for (int numCounter = 0; numCounter < sumCounters.length; numCounter++) {
            sumCounters[numCounter] = new SumCounter("Sum counter " + numCounter, counterSum, LIMIT);
            sumCounters[numCounter].start();
        }

        while (Utils.threadsActive(sumCounters)) {

        }
        System.out.println("Total counter = " + counterSum.getCounter());
    }
}
