package exercisesUnit2;

class Counter {
    private long counter = 0;

    public synchronized void addCounter(long add) {
        counter += add;
    }

    public long getCounter() {

        return counter;
    }
}

class SumCounter extends Thread {
    private final Counter counterSum;
    private long counter = 1;
    private final long limit;

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
        boolean countersActive = true;
        int numCounter = 0;
        while (countersActive) {
            countersActive = sumCounters[numCounter].isAlive();
            if (countersActive || (numCounter == (sumCounters.length - 1))) {
                numCounter = 0;
            } else {
                numCounter++;
            }
        }

        System.out.println("Total counter = " + counterSum.getCounter());
    }
}
