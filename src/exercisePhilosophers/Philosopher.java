package exercisePhilosophers;

import jam.Utils;

/**
 * Philosophers eats and thinks for random time between MIN_SLEEP_TIME and MAX_SLEEP_TIME.
 * To eat, they have to get both forks on left and right hands.
 * If there is a rice bowl, they eat so many rice grains as time spend eating.
 * It terminates when rice bowl (if any) is empty.
 */
public class Philosopher extends Thread {
    private final Forks forks;
    private final RiceBowl riceBowl;
    private final int[] myForks = new int[2];

    /**
     * @param philNumber Philosopher id.
     * @param forks Forks manager.
     * @param riceBowl Rice bowl manager.
     */
    public Philosopher(int philNumber, Forks forks, RiceBowl riceBowl) {
        super("Philosopher [" + philNumber + "]");

        this.forks = forks;
        this.riceBowl = riceBowl;
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
            // If rice bowl is empty, terminate thread.
            if ((riceBowl != null) && (riceBowl.isEmpty())) {
                System.out.println(getName() + ". RICE BOWL IS EMPTY!!");

                return;
            }

            // Only can eat if it gets both forks.
            boolean canEat = forks.getForks(myForks);
            if (canEat) {
                try {
                    System.out.println(getName() + " starts eating for " + eatTime + "s");
                    Thread.sleep(eatTime * 1000);
                    // If there is a rice bowl, takes its rice grains if bowl is not empty.
                    if (riceBowl != null) {
                        System.out.println(getName() + " ---> " + riceBowl.eatRice(eatTime * 1000) +
                                " rice grains remains in the bowl");
                    }
                } catch (InterruptedException e) {
                    forks.releaseForks(myForks);
                    // If tries to eat when bowl is empty
                    System.out.println(e.getMessage());

                    return;
                }
                forks.releaseForks(myForks);
            }
            try {
                if (canEat) {
                    System.out.println(getName() + " ENDS EATING and starts thinking for " + thinkTime + "s");
                } else {
                    System.out.println(getName() + " CANNOT GET FORKS and starts thinking for " + thinkTime + "s");
                }
                Thread.sleep(thinkTime * 1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
