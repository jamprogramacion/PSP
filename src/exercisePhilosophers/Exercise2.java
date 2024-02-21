package exercisePhilosophers;

/**
 * Starts philosophers threads, and terminates when rice bowl is empty.
 */
public class Exercise2 {
    public static void main(String[] args) {
        final int NUM_PHILOSOPHERS = 5;
        final Forks forks = new Forks(NUM_PHILOSOPHERS);
        final RiceBowl riceBowl = new RiceBowl();

        for (int numPhilosopher = 0; numPhilosopher < NUM_PHILOSOPHERS; numPhilosopher++) {
            Philosopher philosopher = new Philosopher(numPhilosopher, forks, riceBowl);
            philosopher.start();
        }
    }
}
