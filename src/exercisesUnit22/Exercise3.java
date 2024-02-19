package exercisesUnit22;

import jam.Utils;

/**
 * Lines within supermarket.
 * Manages going from one line to next, and random line number.
 */
class Lines {
    private final int numLines;

    /**
     * @param numLines Number of lines within supermarket.
     */
    public Lines(int numLines) {
        this.numLines = numLines;
    }

    /**
     * @param lineNumber Line number right now.
     *
     * @return Next line number.
     *         Goes ascending from 0, and when end is reached then goes descending.
     */
    public int nextLine(int lineNumber) {
        int numLine = lineNumber + 1;

        if (numLine == numLines) {
            return -numLines + 1;
        }

        return numLine;
    }

    /**
     * @return Random line number.
     */
    public int randomLine() {
        return Utils.randomInt(0, numLines - 1);
    }
}

/**
 * Thread for thief.
 * Spends TIME_IN_LINE in every line, then goes to next.
 * Terminates when reach NUM_OBJECTS stolen, or when reach NUM_WARNINGS.
 */
class Thief extends Thread {
    private final Lines lines;
    private int numLine = 0;
    private int numObjets = 0;
    private int numWarnings = 0;

    public int getNumLine() {
        return Math.abs(numLine);
    }

    /**
     * @param lines Manager for thief line.
     */
    public Thief(Lines lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Thief {" +
                "line: " + getNumLine() +
                ", objets: " + numObjets +
                ", warnings: " + numWarnings +
                "}";
    }

    @Override
    public void run() {
        final int TIME_IN_LINE = 10;
        final int NUM_OBJECTS = 10;
        final int NUM_WARNINGS = 5;

        super.run();

        while ((numObjets < NUM_OBJECTS) && (numWarnings < NUM_WARNINGS)) {
            System.out.println(this);
            try {
                Thread.sleep(TIME_IN_LINE * 1000L);
                numObjets++;
                numLine = lines.nextLine(numLine);
            } catch (InterruptedException e) {
                numWarnings++;
                numLine = lines.nextLine(numLine);
            }
        }
    }
}

/**
 * Jumps randomly between supermarket lines, spending TIME_IN_LINE in every one.
 */
class Camera extends Thread {
    private static final int TIME_IN_LINE = 3;
    private final Lines lines;
    private int numLine;

    /**
     * @param lines  Manager for camera line.
     */
    public Camera(Lines lines) {
        this.lines = lines;
        numLine = lines.randomLine();
    }

    @Override
    public String toString() {
        return "Camera {" +
                "line: " + numLine +
                '}';
    }

    public int getNumLine() {
        return numLine;
    }

    @Override
    public void run() {
        super.run();

        while (true) {
            System.out.println(this);
            try {
                Thread.sleep(TIME_IN_LINE * 1000L);
                numLine = lines.randomLine();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

/**
 * Starts camera and thief threads.
 * If camera and thief are in same line, then warns thief.
 */
public class Exercise3 {
    public static void main(String[] args) {
        final int NUM_LINES = 10;
        Lines lines = new Lines(NUM_LINES);
        Thief thief = new Thief(lines);
        Camera camera = new Camera(lines);

        camera.start();
        thief.start();
        while (thief.isAlive()) {
            if (camera.getNumLine() == thief.getNumLine()) {
                thief.interrupt();
            }
        }
        camera.interrupt();

        System.out.println();
        System.out.println(thief);
    }
}
