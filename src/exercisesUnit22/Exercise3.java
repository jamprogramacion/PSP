package exercisesUnit22;

import jam.Utils;

class Lines {
    private int numLines;

    public Lines(int numLines) {
        this.numLines = numLines;
    }
    public int nextLine(int lineNumber) {
        if (lineNumber < (numLines - 1)) {
            return lineNumber + 1;
        } else {
            return 0;
        }
    }

    public int randomLine() {
        return Utils.randomInt(0, numLines - 1);
    }
}
class Thief extends Thread {
    private static final int TIME_IN_LINE = 10;
    private static final int NUM_OBJECTS = 10;
    private static final int NUM_WARNINGS = 5;
    private final Lines lines;
    private int numLine = 0;
    private int numObjets = 0;
    private int numWarnings = 0;
    private int direction = 1;

    public int getNumLine() {
        return numLine;
    }

    public Thief(Lines lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Thief {" +
                "Objets: " + numObjets +
                ", Warnings: " + numWarnings +
                "}";
    }

    @Override
    public void run() {
        super.run();

        while ((numObjets < NUM_OBJECTS) && (numWarnings < NUM_WARNINGS)) {
            try {
                Thread.sleep(TIME_IN_LINE * 1000L);
                numObjets++;
                lines.nextLine(numLine);
            } catch (InterruptedException e) {
                numWarnings++;
                lines.nextLine(numLine);
            }
        }
    }
}

class Camera extends Thread {
    private static final int TIME_IN_LINE = 3;
    private final Lines lines;
    private int numLine;

    public Camera(Lines lines) {
        this.lines = lines;
        numLine = lines.randomLine();
    }

    public int getNumLine() {
        return numLine;
    }

    @Override
    public void run() {
        super.run();

        try {
            Thread.sleep(TIME_IN_LINE * 1000L);
            lines.randomLine();
        } catch (InterruptedException ignore) {

        }
    }
}

public class Exercise3 {
    private static final int NUM_LINES = 10;
    private static final Lines lines = new Lines(NUM_LINES);
    private static final Thief thief = new Thief(lines);
    private static final Camera camera = new Camera(lines);

    public static void main(String[] args) {

    }
}
