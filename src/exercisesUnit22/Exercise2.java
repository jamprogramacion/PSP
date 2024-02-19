package exercisesUnit22;

import jam.Utils;

import java.util.Arrays;

/**
 * Class to manage all cashes within supermarket.
 */
class MultiCash {
    private final long[] total;

    /**
     * @param numCashes Init cashes array, to store total amount in them.
     */
    public MultiCash(int numCashes) {
        total = new long[numCashes];
        Arrays.fill(total, 0);
    }

    /**
     * @return Number of cashes within supermarket.
     */
    public int length() {
        return total.length;
    }

    /**
     * @param numCash Cash number.
     *
     * @return Amount in cash.
     */
    public long getTotal(int numCash) {
        return total[numCash];
    }

    /**
     * @param numCash Cash number.
     * @param pay Amount to add to the cash.
     *
     * @return Total amount in the cash.
     */
    public synchronized long addCash(int numCash, int pay) {
        total[numCash] += pay;

        return total[numCash];
    }
}

/**
 * Client thread, buys a random amount, random time shopping
 * and random cash number.
 */
class Client2 extends Thread {
    private final int BUY_AMOUNT = Utils.randomInt(100, 200);
    private final int BUY_TIME = Utils.randomInt(3, 7);
    private final MultiCash payCash;
    private final int numCash;

    /**
     * @param clientNumber Client id.
     * @param payCash Cashes within supermarket.
     */
    public Client2(int clientNumber, MultiCash payCash) {
        super(String.valueOf(clientNumber));

        numCash = Utils.randomInt(0, payCash.length() - 1);
        this.payCash = payCash;
    }

    @Override
    public String toString() {
        return "Client [" + getName() + "]. " +
                "Cash number: " + numCash + ", " +
                "spent: " + BUY_AMOUNT + "€, " +
                "time: " + BUY_TIME + "s";
    }

    @Override
    public void run() {
        super.run();

        System.out.println("Client [" + getName() + "] starts shopping...");
        try {
            Thread.sleep(BUY_TIME * 1000L);
            System.out.print(this);
            System.out.println(", total in cash [" + numCash + "]: " + payCash.addCash(numCash, BUY_AMOUNT) + "€.");
        } catch (InterruptedException ignore) {

        }
    }
}

/**
 * Starts client threads every SLEEP_TIME and wait until all clients have paid.
 * Then shows total amount in every cash.
 */
public class Exercise2 {
    public static void main(String[] args) {
        final int SLEEP_TIME = 1000;
        final int NUM_CASHES = 4;
        final int NUM_CLIENTS = 10;
        MultiCash superCashes = new MultiCash(NUM_CASHES);
        Client2[] clients = new Client2[NUM_CLIENTS];

        for (int numClient = 0; numClient < NUM_CLIENTS; numClient++) {
            clients[numClient] = new Client2(numClient + 1, superCashes);
            clients[numClient].start();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ignore) {

            }
        }
        while (Utils.threadsActive(clients));

        System.out.println();
        for (int numCash = 0; numCash < superCashes.length(); numCash++) {
            System.out.println("Total in cash number " + numCash + ": " + superCashes.getTotal(numCash) + "€");
        }
    }
}
