package ut1.exercise1;

import jam.Utils;
import java.io.File;
import java.io.IOException;

public class Exercise1 {
    public static void main(String[] args) {
        String procName ="ut1.exercise1.Exercise1Proc";
        String pathProcClass = System.getProperty("user.dir") + "\\out\\production\\PSP";
        String outFilesPath = System.getProperty("user.dir") + "\\src\\ut1\\exercise1\\";

        int num1 = Utils.inputInt("Enter two integers, both 0 to exit:", null);
        int num2 = Utils.inputInt("Enter two integers, both 0 to exit:", null);
        boolean exit = (num1 == 0) && (num2 == 0);
        while (!exit) {
            ProcessBuilder procSeries = new ProcessBuilder("java", procName, String.valueOf(num1), String.valueOf(num2));
            procSeries.redirectOutput(new File(outFilesPath + num1 + "_" + num2 + ".txt"));
            procSeries.redirectError(new File(outFilesPath + num1 + "_" + num2 + "_errors.txt"));
            procSeries.directory(new File(pathProcClass));
            try {
                procSeries.start();
            } catch (IOException e) {
                System.err.println("Error opening file");
            }

            num1 = Utils.inputInt("Enter two integers, both 0 to exit:", null);
            num2 = Utils.inputInt("Enter two integers, both 0 to exit:", null);
            exit = (num1 == 0) && (num2 == 0);
        }
    }
}
