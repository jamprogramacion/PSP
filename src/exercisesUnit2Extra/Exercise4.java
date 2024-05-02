package exercisesUnit2Extra;

import jam.Utils;

class Queue {
    private static final int NUM_CUSTOMERS = 20;
    private int numCustomer = 0;
    private int outCustomers = 0;

    public synchronized int nextCustomer() {
        if (numCustomer == -1) {
            return -1;
        }

        numCustomer++;
        if (numCustomer > NUM_CUSTOMERS) {
            numCustomer = -1;
        }

        return numCustomer;
    }

    public synchronized int incOutCustomers() {
        return ++outCustomers;
    }

    public int getOutCustomers() {
        return outCustomers;
    }

    public int getNumCustomers() {
        return NUM_CUSTOMERS;
    }
}

class CustomerServiceAgent implements Runnable {
    private static final long TIME_EMERGENCY = 3;
    private static final long TIME_BOSS = 5;
    private final Queue queue;
    private final int numWindow;

    public CustomerServiceAgent(Queue queue, int numWindow) {
        this.queue = queue;
        this.numWindow = numWindow;
    }

    @Override
    public void run() {
        long MAX_INTERRUPTIONS = Utils.randomInt(3, 5);
        long TIME_CUSTOMER = Utils.randomInt(5, 10);
        boolean interrupted = false;
        int numInterruptions = 0;

        int numCustomer = queue.nextCustomer();
        while ((numCustomer != -1) && (numInterruptions < MAX_INTERRUPTIONS)) {
            System.out.println("Window [" + numWindow + "] " + (interrupted ? "continues" : "begins") + " with customer [" + numCustomer + "] for [" + TIME_CUSTOMER + "] seconds...");
            try {
                Thread.sleep(TIME_CUSTOMER * 1000);
                interrupted = false;
                System.out.println("Window [" + numWindow + "] saying good bye to customer [" + numCustomer + "]");
                System.out.println(">>> Customers attended to: " + queue.incOutCustomers() + "/" + queue.getNumCustomers());
                numCustomer = queue.nextCustomer();
            } catch (InterruptedException e) {
                interrupted = true;
                numInterruptions++;
                long intType = Utils.randomInt(1,2);
                String call = intType == 1 ? "boss" : "emergency";
                System.out.println("Window [" + numWindow + "] stopped for " + call + " call (" + numInterruptions + "/" + MAX_INTERRUPTIONS + ")!!");
                try {
                    Thread.sleep(1000 * (intType == 1 ? TIME_BOSS : TIME_CUSTOMER));
                } catch (InterruptedException ex) {
                    System.out.println("---->>>> Window [" + numWindow + "] busy with " + call + ", cannot be interrupted!!");
                }
            }
        }
        if (numInterruptions == MAX_INTERRUPTIONS) {
            System.out.println("---->>>> Window [" + numWindow + "] closed because of max interruptions reached!!");

            return;
        } else if (numCustomer == -1) {
            System.out.println("---->>>> Window [" + numWindow + "] closed because of end of customers queue");

            return;
        }
    }
}

public class Exercise4 {
    public static void main(String[] args) throws InterruptedException {
        final int NUM_WINDOWS = 5;
        Queue queue = new Queue();
        Thread[] windows = new Thread[NUM_WINDOWS];

        for (int numWindow = 0; numWindow < NUM_WINDOWS; numWindow++) {
            windows[numWindow] = new Thread(new CustomerServiceAgent(queue, numWindow));
            windows[numWindow].start();
        }

        Thread.sleep(Utils.randomLong(5, 10) * 1000);
        int numWindow = 0;
        while (Utils.threadsActive(windows)) {
            if (windows[numWindow].isAlive() && (!windows[numWindow].isInterrupted())) {
                windows[numWindow].interrupt();
                Thread.sleep(Utils.randomLong(5, 10) * 1000);
            }
            numWindow++;
            if (numWindow == NUM_WINDOWS) {
                numWindow = 0;
            }
        }

        System.out.println("Customers attended to: " + queue.getOutCustomers() + "/" + queue.getNumCustomers());
    }
}
