package exercisesUnit2Extra;

import jam.Utils;

class Queue {
    private static final int NUM_CUSTOMERS = 20;
    private int numCustomer = 0;

    public synchronized int nextCustomer() {
        numCustomer++;
        if (numCustomer > NUM_CUSTOMERS) {
            numCustomer = 0;
        }

        return numCustomer;
    }
}

class CustomerServiceAgent implements Runnable {
    private static final long TIME_EMERGENCY = 3;
    private static final long TIME_BOSS = 5;
    private Queue queue;
    private int numWindow;
    private boolean closed = false;

    public CustomerServiceAgent(Queue queue, int numWindow) {
        this.queue = queue;
        this.numWindow = numWindow;
    }

    @Override
    public void run() {
        long MAX_INTERRUPTIONS = Utils.randomInt(1, 3);
        long TIME_CUSTOMER = Utils.randomInt(5, 10);
        int numInterruptions = 0;

        int numCustomer = queue.nextCustomer();
        boolean interrupted = false;
        while ((numCustomer != 0) && (numInterruptions < MAX_INTERRUPTIONS)) {
            System.out.println("Window [" +numWindow + "] " + (interrupted ? "continues" : "begins") + " with customer [" + numCustomer + "] for [" + TIME_CUSTOMER + "] seconds...");
            try {
                Thread.sleep(TIME_CUSTOMER * 1000);
                System.out.println("Saying good bye to customer [" + numCustomer + "]");
                numCustomer = queue.nextCustomer();
                interrupted = false;
            } catch (InterruptedException e) {
                interrupted = true;
                numInterruptions++;
                long intType = Utils.randomInt(1,2);
                String call = intType == 1 ? "boss" : "emergency";
                System.out.println("Stopped for " + call + " call (" + numInterruptions + "/" + MAX_INTERRUPTIONS + ")!!");
                try {
                    Thread.sleep(1000 * (intType == 1 ? TIME_BOSS : TIME_CUSTOMER));
                } catch (InterruptedException ex) {
                    System.out.println("Worker busy with " + call + ", cannot be interrupted!!");
                }
            }
            if (numInterruptions == MAX_INTERRUPTIONS) {
                System.out.println("Window closed because of max interruptions reached!!");
            } else  {
                System.out.println("Window closed because of end of customers queue");
            }
            closed = true;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isInterrupted() {
        return Thread.interrupted();
    }
}

public class Exercise4 {
    public static void main(String[] args) {
        final int NUM_WINDOWS = 3;
        CustomerServiceAgent[] windows = new CustomerServiceAgent[NUM_WINDOWS];

        for (int numWindow = 0; numWindow < NUM_WINDOWS; numWindow++) {
            windows[numWindow] = new CustomerServiceAgent(new Queue(), numWindow);
            windows[numWindow].run();
        }

        int numWindow = 0;
        boolean allWindowsClosed = false;
    }
}
