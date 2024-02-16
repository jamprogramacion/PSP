package exercisesUnit22;

import jam.Utils;

class Cash {
    private long total = 0;

    public synchronized long addCash(int pay) {
        total += pay;

        return total;
    }
}

class Client extends Thread {
    private final int BUY_AMOUNT = Utils.randomInt(100, 200);
    private final int BUY_TIME = Utils.randomInt(3, 7);
    private final Cash payCash;

    public Client(int clientNumber, Cash payCash) {
        super(String.valueOf(clientNumber));

        this.payCash = payCash;
    }

    @Override
    public String toString() {
        return "Client [" + getName() + "]. " +
                "Spent: " + BUY_AMOUNT + "€, " +
                "time: " + BUY_TIME + "s";
    }

    @Override
    public void run() {
        super.run();

        System.out.println("Client [" + getName() + "] starts shopping...");
        try {
            Thread.sleep(BUY_TIME * 1000L);
            System.out.print(this);
            System.out.println(", total in cash: " + payCash.addCash(BUY_AMOUNT) + "€.");
        } catch (InterruptedException ignore) {

        }
    }
}
public class Exercise1 {
    public static void main(String[] args) {
        final int SLEEP_TIME = 500;
        Cash superCash = new Cash();
        int numClient = 1;

        while (true) {
            new Client(numClient, superCash).start();
            numClient++;
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
