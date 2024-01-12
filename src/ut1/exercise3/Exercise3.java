package ut1.exercise3;

import jam.Utils;
import java.io.File;
import java.io.IOException;

public class Exercise3 {
    public static void main(String[] args) {
        String procName ="ut1.exercise3.Exercise3Proc";
        String pathProcClass = "D:\\jamprogramacion\\FPAppMultiPlat\\IntelliJ\\PSP\\out\\production\\PSP";
        String outFilesPath = "D:\\jamprogramacion\\FPAppMultiPlat\\IntelliJ\\PSP\\src\\ut1\\exercise3\\";
        int numProcs = 4;

        int num1 = Utils.inputInt("Enter two integers, both 0 to exit:", null);
        int num2 = Utils.inputInt("Enter two integers, both 0 to exit (this 2nd, at least 1000 greater/smaller than 1st):", null);
        if ((num1 == 0) && (num2 == 0)) {
            return;
        }

        while (Math.abs(num1 - num2) < 1000) {
            num2 = Utils.inputInt("Enter two integers, both 0 to exit (this 2nd, at least 1000 greater/smaller than 1st):", null);
        }
        int steps = (num1 - num2) / numProcs;
        ProcessBuilder[] procSumSeries = new ProcessBuilder[numProcs];
        for (int cont = 0; cont < numProcs; cont++) {
            procSumSeries[cont] = new ProcessBuilder("java", procName, String.valueOf(num1 + steps * cont),
                    String.valueOf(num1 + steps * (cont + 1) - 1));
            procSumSeries[cont].redirectOutput(new File(outFilesPath +
                    (num1 + steps * cont) + "_" + (num1 + steps * (cont + 1) - 1) + ".txt"));
            procSumSeries[cont].redirectError(new File(outFilesPath +
                    (num1 + steps * cont) + "_" + (num1 + steps * (cont + 1) - 1) + "_errors.txt"));
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
