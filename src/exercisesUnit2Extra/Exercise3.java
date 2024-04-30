package exercisesUnit2Extra;

import jam.Utils;

import java.util.concurrent.PriorityBlockingQueue;

class PrintingJob implements Runnable, Comparable {
    private static int PRINT_TIME_MIN = 1;
    private static int PRINT_TIME_MAX = 5;
    private int jobNumber;
    private int jobPriority;

    public PrintingJob(int jobNumber, int jobPriority) {
        this.jobNumber = jobNumber;
        this.jobPriority = jobPriority;
    }

    @Override
    public void run() {
        long printTime = Utils.randomInt(PRINT_TIME_MIN, PRINT_TIME_MAX);
        System.out.println("Print job [" + jobNumber + "] priority [" + jobPriority + "] starts printing for " + printTime + " seconds...");
        try {
            Thread.sleep(printTime * 1000);
            System.out.println("Print job [" + jobNumber + "] priority [" + jobPriority + "] finished!!");
        } catch (InterruptedException e) {
            System.out.println("Print job [" + jobNumber + "] interrupted!!");
        }
    }

    @Override
    public int compareTo(Object o) {
        if (jobNumber < ((PrintingJob) o).jobNumber) {
            return -1;
        }

        if (jobNumber > ((PrintingJob) o).jobNumber) {
            return 1;
        }

        return 0;
    }
}

class PrintingSystem {
    private PriorityBlockingQueue<PrintingJob> printQueue = new PriorityBlockingQueue<>();

    public Thread AddPrintJob(int jobNumber, int priority) {
        PrintingJob printJob = new PrintingJob(jobNumber, priority);
        Thread job = new Thread(printJob);
        job.setPriority(priority);
        printQueue.add(printJob);

        return job;
    }

    public void PrintJobs() throws InterruptedException {
        while (printQueue.size() > 0) {
            printQueue.take().run();
        }
    }
}

public class Exercise3 {
    private static int NUM_JOBS = 10;

    public static void main(String[] args) throws InterruptedException {
        PrintingSystem printer = new PrintingSystem();
        Thread[] jobs = new Thread[NUM_JOBS];

        for (int job = 0; job < NUM_JOBS; job++) {
            jobs[job] = printer.AddPrintJob(job, Utils.randomInt(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY));
        }

        printer.PrintJobs();
    }
}
