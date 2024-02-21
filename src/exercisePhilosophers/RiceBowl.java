package exercisePhilosophers;

/**
 * Rice bowl manager.
 */
public class RiceBowl {
    private final long RICE_GRAINS = 50000;
    private long eatenGrains = 0;

    /**
     * @param grains Number of rice grains to eat.
     *
     * @return Number of rice grains remaining on the bowl.
     *
     * @throws InterruptedException where rice bowl is empty.
     */
    public synchronized long eatRice(long grains) throws InterruptedException {
        eatenGrains += grains;
        if (eatenGrains >= RICE_GRAINS) {
            eatenGrains = RICE_GRAINS;

            throw new InterruptedException("RICE BOWL IS EMPTY!!");
        }

        return RICE_GRAINS - eatenGrains;
    }

    /**
     * @return true if rice bowl is empty.
     */
    public boolean isEmpty() {
        return RICE_GRAINS == eatenGrains;
    }
}
