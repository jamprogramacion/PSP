package examen.ejercicio1;

import java.io.File;
import java.io.IOException;

public class Ejercicio1 {
    public static void main(String[] args) {
        String procName ="examen.ejercicio1.Ejercicio1Proc";
        String pathProcClass = System.getProperty("user.dir") + "\\out\\production\\PSP";
        String outFilesPath = System.getProperty("user.dir") + "\\src\\examen\\ejercicio1\\";

        for (int numProc = 1; numProc <= 10; numProc++) {
            ProcessBuilder procSeries = new ProcessBuilder("java", procName, String.valueOf(numProc));
            procSeries.redirectOutput(new File(outFilesPath + numProc + ".txt"));
            procSeries.directory(new File(pathProcClass));
            try {
                procSeries.start();
            } catch (IOException e) {
                System.err.println("Error opening file");
            }
        }
    }
}
