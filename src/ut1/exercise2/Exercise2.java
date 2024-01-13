package ut1.exercise2;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Exercise2 {
    public static void main(String[] args) {
        String procName ="ut1.exercise2.Exercise2Proc";
        String pathProcClass = System.getProperty("user.dir") + "\\out\\production\\PSP";
        String outFilesPath = System.getProperty("user.dir") + "\\src\\ut1\\exercise2\\";

        String sentence = JOptionPane.showInputDialog("Enter a sentence to count chars and words:");
        ProcessBuilder procSentence = new ProcessBuilder("java", procName, sentence);
        procSentence.redirectOutput(new File(outFilesPath + "analisisDeFrase.txt"));
        procSentence.redirectError(new File(outFilesPath + "analisisDeFrase_errors.txt"));
        procSentence.directory(new File(pathProcClass));
        try {
            procSentence.start();
        } catch (IOException e) {
            System.err.println("Error opening file");
        }
    }
}
