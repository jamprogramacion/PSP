package exercisesUnit2Extra;

import jam.Utils;

import java.util.Arrays;

/**
 * Encapsulates an array to store all partial sums from threads.
 */
class ArraySum {
    private final int[] arrayThreadSums;

    /**
     * @param numThreads Number of threads.
     */
    public ArraySum(int numThreads) {
        arrayThreadSums = new int[numThreads];
        Arrays.fill(arrayThreadSums, 0);
    }

    /**
     * Increments the partial sum with value.
     *
     * @param numThread Thread number.
     * @param value Value to increment partial sum.
     *
     * @return Total of partial sum at this moment.
     */
    public synchronized int incSum(int numThread, int value) {
        arrayThreadSums[numThread] += value;

        return arrayThreadSums[numThread];
    }

    @Override
    public String toString() {
        return "Threads sums: " + Arrays.toString(arrayThreadSums);
    }

    /**
     * @return Array with partial sums.
     */
    public int[] getArrayThreadSums() {
        return arrayThreadSums;
    }
}

/**
 * Class for threads that make the partial sums.
 */
class SumElements extends Thread {
    private final ArraySum arraySum;
    private final int[] arrayInt;
    private final int sumFrom;
    private final int sumTo;
    private final int numThread;

    /**
     * @param numThread Thread number.
     * @param arraySum ArraySum class to make partial sums.
     * @param arrayInt Array with integers to sum.
     * @param sumFrom Index from which starts to sum (included).
     * @param sumTo Index to stop the sum (included).
     */
    public SumElements(int numThread, ArraySum arraySum, int[] arrayInt, int sumFrom, int sumTo) {
        super("Thread number " + numThread);

        this.arraySum = arraySum;
        this.arrayInt = arrayInt;
        this.sumFrom = sumFrom;
        this.sumTo = sumTo;
        this.numThread = numThread;
    }

    @Override
    public void run() {
        super.run();

        System.out.println("---->" + getName() + " started. Sum values from " + sumFrom + " to " + sumTo);
        for (int value = sumFrom; value <= sumTo; value++) {
            System.out.println(getName() + ". Summing array[" + value + "] = " + arrayInt[value] +
                    ". Partial sum goes " + arraySum.incSum(numThread, arrayInt[value]));
        }
        System.out.println(getName() + " finished.");
    }
}

public class Exercise1 {
    private static final int NUM_THREADS = 5;
    private static final int NUM_ELEMENTS = 1003;
    private final static int[] array = new int[NUM_ELEMENTS];
    private final static ArraySum arraySum = new ArraySum(NUM_THREADS);

    public static void main(String[] args) {
        for (int value = 0; value < array.length; value++) {
            array[value] = Utils.randomInt(1, 10);
        }

        SumElements[] thrSum = new SumElements[NUM_THREADS];
        for (int thr = 0; thr < NUM_THREADS; thr++) {
            int sumFrom = thr * (NUM_ELEMENTS / NUM_THREADS);
            int sumTo = thr < (NUM_THREADS - 1) ? (thr + 1) * (NUM_ELEMENTS / NUM_THREADS) - 1 : NUM_ELEMENTS - 1;
            thrSum[thr] = new SumElements(thr, arraySum, array, sumFrom, sumTo);
            thrSum[thr].start();
        }

        while (Utils.threadsActive(thrSum)) {

        }

        System.out.println("Partial sums: " + arraySum);
        int total = 0;
        for (int value : arraySum.getArrayThreadSums()) {
            total += value;
        }
        System.out.println("Total sum: " + total);
    }
}
