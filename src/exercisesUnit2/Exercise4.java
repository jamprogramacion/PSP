package exercisesUnit2;

/**
 * Thread that repeats a message every given time, until numMessages reached.
 */
class RepeatMessage extends Thread {
    private static final int SLEEP_TIME = 500;
    private final int numMessages;

    /**
     * @param name Thread id.
     * @param numMessages Number of times message is repeated.
     */
    public RepeatMessage(String name, int numMessages) {
        super(name);

        this.numMessages = numMessages;
    }

    @Override
    public void run() {
        super.run();

        try {
            for (int numMessage = 1; numMessage <= numMessages; numMessage++) {
                Thread.sleep(SLEEP_TIME);
                System.out.println("Thread [" + this.getName() + "]. Message number " + numMessage);
            }
        } catch (InterruptedException e) {
            return;
        }

        System.out.println("Thread [" + this.getName() + "] finished");
    }
}

/**
 * Starts some thread to repeat messages.
 * Every thread has its priority.
 */
public class Exercise4 {
    static final int NUM_THREADS = 10;
    static final int NUM_MESSAGES = 10;
    private static final RepeatMessage[] msgThread = new RepeatMessage[NUM_THREADS];

    public static void main(String[] args) {
        for (int numThr = 0; numThr < msgThread.length; numThr++) {
            msgThread[numThr] = new RepeatMessage("number " + (numThr + 1), NUM_MESSAGES);
            msgThread[numThr].setPriority(numThr + 1);
        }
        for (RepeatMessage repeatMessage : msgThread) {
            repeatMessage.start();
        }
    }
}
