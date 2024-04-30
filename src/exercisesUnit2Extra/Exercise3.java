package exercisesUnit2Extra;

import jam.Utils;

import java.util.concurrent.PriorityBlockingQueue;

class PrintingSystem {
    private final PriorityBlockingQueue<PrintingJob> printQueue = new PriorityBlockingQueue<>();

    public synchronized void AddPrintJob(PrintingJob job) {
        printQueue.add(job);
    }

    public int Print() throws InterruptedException {
        printQueue.take().run();

        return printQueue.size();
    }
}

class PrintingJob implements Runnable, Comparable<PrintingJob> {
    private final int jobNumber;
    private final int jobPriority;

    public PrintingJob(int jobNumber, int jobPriority) {
        this.jobNumber = jobNumber;
        this.jobPriority = jobPriority;
    }

    @Override
    public void run() {
        int PRINT_TIME_MIN = 1;
        int PRINT_TIME_MAX = 5;

        long printTime = Utils.randomInt(PRINT_TIME_MIN, PRINT_TIME_MAX);
        System.out.println("Print job [" + jobNumber + "] priority [" + jobPriority + "] starts printing for " + printTime + " seconds...");
        try {
            Thread.sleep(printTime * 1000);
            System.out.println("Print job [" + jobNumber + "] finished!!");
        } catch (InterruptedException e) {
            System.out.println("Print job [" + jobNumber + "] interrupted!!");
        }
    }

    public int getJobPriority() {
        return this.jobPriority;
    }

    @Override
    public int compareTo(PrintingJob o) {
        return Integer.compare(jobPriority, o.getJobPriority()) * (-1);
    }
}

public class Exercise3 {
    public static void main(String[] args) throws InterruptedException {
        int NUM_JOBS = 10;
        PrintingSystem printer = new PrintingSystem();
        PrintingJob[] jobs = new PrintingJob[NUM_JOBS];

        for (int job = 0; job < NUM_JOBS; job++) {
            jobs[job] = new PrintingJob(job, Utils.randomInt(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY));
            printer.AddPrintJob(jobs[job]);
        }

        while (printer.Print() > 0) {

        }
    }
}
