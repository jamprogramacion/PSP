package examen.ejercicio1;

import examen.Utils;

public class Ejercicio1Proc {
    public static void main(String[] args) {
        int numProc;

        try {
            numProc = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Argument not an integer.");

            return;
        }

        System.out.println(Utils.randomInt(numProc * 10, numProc * 10 + 10));
    }
}
