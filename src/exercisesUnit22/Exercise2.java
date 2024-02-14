package exercisesUnit22;

import jam.Utils;

import java.util.Arrays;

class MultiCash {
    private final long[] total;

    public MultiCash(int numCashes) {
        total = new long[numCashes];
        Arrays.fill(total, 0);
    }

    public int length() {
        return total.length;
    }

    public long getTotal(int numCash) {
        return total[numCash];
    }

    public synchronized long addCash(int numCash, int pay) {
        total[numCash] += pay;

        return total[numCash];
    }
}

class Client2 extends Thread {
    private final int BUY_AMOUNT = Utils.randomInt(100, 200);
    private final int BUY_TIME = Utils.randomInt(3, 7);
    private final MultiCash payCash;
    private final int numCash;

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
