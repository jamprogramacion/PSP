package exercisePhilosophers;

public class Exercise1 {
    public static void main(String[] args) {
        final int NUM_PHILOSOPHERS = 5;
        final Forks forks = new Forks(NUM_PHILOSOPHERS);

        for (int numPhilosopher = 0; numPhilosopher < NUM_PHILOSOPHERS; numPhilosopher++) {
            Philosopher philosopher = new Philosopher(numPhilosopher, forks);
            philosopher.start();
        }
    }
}
