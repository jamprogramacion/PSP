package exercisePhilosophers;

import java.util.concurrent.Semaphore;

/**
 * Forks manager.
 */
public class Forks {
    private final Semaphore[] forks;

    /**
     * @param numForks Number of available forks.
     */
    public Forks(int numForks) {
        forks = new Semaphore[numForks];
        for (int numFork = 0; numFork < numForks; numFork++) {
            forks[numFork] = new Semaphore(1);
        }
    }

    /**
     * @param numForks array with two forks to try to get them.
     *
     * @return true if the two forks are available.
     */
    public boolean getForks(int[] numForks) {
        boolean leftFork = forks[numForks[0]].tryAcquire(1);
        boolean rightFork = forks[numForks[1]].tryAcquire(1);

        if (leftFork && rightFork) {
            return true;
        }

        if (leftFork) {
            forks[numForks[0]].release();
        }
        if (rightFork) {
            forks[numForks[1]].release();
        }

        return false;
    }

    /**
     * @param numForks Release the two forks in the array.
     */
    public void releaseForks(int[] numForks) {
        forks[numForks[0]].release();
        forks[numForks[1]].release();
    }

    /**
     * @return Total number of forks on the table.
     */
    public int length() {
        return forks.length;
    }
}
