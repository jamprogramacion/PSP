package exercisesUnit2Extra;

import jam.Utils;

class LibraryCounter {
    private int visitorCounter = 0;
    private int bookCounter = 0;
    private int comicCounter = 0;

    public synchronized int incVisitorCounter() {
        return ++visitorCounter;
    }

    public synchronized int incBookCounter() {
        return ++bookCounter;
    }

    public synchronized int incComicCounter() {
        return ++comicCounter;
    }

    public int getBookCounter() {
        return bookCounter;
    }

    public int getComicCounter() {
        return comicCounter;
    }

    public int getVisitorCounter() {
        return visitorCounter;
    }
}

class LibraryVisitor implements Runnable {
    private final LibraryCounter libraryCounter;

    public LibraryVisitor(LibraryCounter libraryCounter) {
        this.libraryCounter = libraryCounter;
    }

    @Override
    public void run() {
        final int MIN_TIME = 3;
        final int MAX_TIME = 7;
        final String[] RESOURCE = {"Book", "Comic"};
        long time = Utils.randomInt(MIN_TIME, MAX_TIME);
        int res = Utils.randomInt(0, RESOURCE.length - 1);

        try {
            System.out.println("Visitor number " + libraryCounter.incVisitorCounter() + " gets into library for " + time + " seconds, to read a " + RESOURCE[res] + "...");
            switch (res) {
                 case 0: libraryCounter.incBookCounter();
                    break;
                case 1: libraryCounter.incComicCounter();
                    break;
            }
            Thread.sleep(time * 1000);
            System.out.println("--->>> Visitor number " + libraryCounter.incVisitorCounter() + " gets out library");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Exercise5 {
}
