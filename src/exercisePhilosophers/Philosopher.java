package exercisePhilosophers;

import jam.Utils;

public class Philosopher extends Thread {
    private final Forks forks;
    private final int[] myForks = new int[2];

    public Philosopher(int philNumber, Forks forks) {
        super("Philosopher [" + philNumber + "]");

        this.forks = forks;
        myForks[0] = philNumber;
        myForks[1] = (philNumber < forks.length() - 1) ? philNumber + 1 : 0;
    }

    @Override
    public void run() {
        final int MIN_SLEEP_TIME = 1;
        final int MAX_SLEEP_TIME = 10;
        final long eatTime = Utils.randomInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME);
        final long thinkTime = Utils.randomInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME);

        super.run();

        while (true) {
            boolean canEat = forks.getForks(myForks);
            if (canEat) {
                try {
                    System.out.println(getName() + " starts eating for " + eatTime + "s");
                    Thread.sleep(eatTime * 1000);
                } catch (InterruptedException e) {
                    forks.releaseForks(myForks);

                    return;
                }
                forks.releaseForks(myForks);
            }
            try {
                if (canEat) {
                    System.out.println(getName() + " ENDS EATING and starts thinking for " + thinkTime + "s");
                } else {
                    System.out.println(getName() + " CANNOT GER FORKS and starts thinking for " + thinkTime + "s");
                }
                Thread.sleep(thinkTime * 1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
