package exercisePhilosophers;

import java.util.concurrent.Semaphore;

public class Forks {
    private final Semaphore[] forks;

    public Forks(int numForks) {
        forks = new Semaphore[numForks];
        for (int numFork = 0; numFork < numForks; numFork++) {
            forks[numFork] = new Semaphore(1);
        }
    }

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

    public void releaseForks(int[] numForks) {
        forks[numForks[0]].release();
        forks[numForks[1]].release();
    }

    public int length() {
        return forks.length;
    }
}
