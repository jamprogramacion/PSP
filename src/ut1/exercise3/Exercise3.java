package ut1.exercise3;

import jam.UtilsSwing;
import java.io.File;
import java.io.IOException;

public class Exercise3 {
    public static void main(String[] args) {
        String procName ="ut1.exercise3.Exercise3Proc";
        String pathProcClass = System.getProperty("user.dir") + "\\out\\production\\PSP";
        String outFilesPath = System.getProperty("user.dir") + "\\src\\ut1\\exercise3\\";
        /* Number of process to create */
        int numProcs = 4;

        int num1 = UtilsSwing.inputInt("Enter two integers, both 0 to exit:", null);
        int num2 = UtilsSwing.inputInt("Enter two integers, both 0 to exit\n(this 2nd, at least 1000 greater/smaller than 1st):", null);
        if ((num1 == 0) && (num2 == 0)) {
            return;
        }

        while (Math.abs(num2 - num1) < 1000) {
            num2 = UtilsSwing.inputInt("Enter two integers, both 0 to exit\n(this 2nd, at least 1000 greater/smaller than 1st):", null);
        }
        /* How many numbers to sum in each process */
        int steps = (num2 - num1 + 1) / numProcs;
        /* Array or the process */
        ProcessBuilder[] procSumSeries = new ProcessBuilder[numProcs];
        for (int cont = 0; cont < numProcs; cont++) {
            /* If in the last iteration, last number has to be num2, because maybe (num2 - num1) is not divisible
            by numProcs and then last number is smaller than num2 */
            int lastNumber = cont < (numProcs - 1) ? num1 + steps * (cont + 1) - 1 : num2;
            procSumSeries[cont] = new ProcessBuilder("java", procName, String.valueOf(num1 + steps * cont),
                    String.valueOf(lastNumber));
            procSumSeries[cont].redirectOutput(new File(outFilesPath + (num1 + steps * cont) +
                    "_" + lastNumber + ".txt"));
            procSumSeries[cont].redirectError(new File(outFilesPath + (num1 + steps * cont) +
                    "_" + lastNumber + "_errors.txt"));
            procSumSeries[cont].directory(new File(pathProcClass));
        }
        try {
            for (int cont = 0; cont < numProcs; cont++) {
                procSumSeries[cont].start();
            }
        } catch (IOException e) {
            System.err.println("Error opening file");
        }
    }
}
