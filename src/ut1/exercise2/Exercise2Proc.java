package ut1.exercise2;

import java.lang.Character;

/**
 * Takes one string and outs a count of letters and words.
 */
public class Exercise2Proc {
    public static void main(String[] args) {
        int numChars = Character.isLetter(args[0].charAt(0)) ? 1 : 0;
        int numWords = 0;

        for (int cont = 1; cont < args[0].length(); cont++) {
            if (Character.isLetter(args[0].charAt(cont))) {
                numChars++;
            } else if (Character.isLetter(args[0].charAt(cont - 1))) {
                numWords++;
            }
        }
        if (Character.isLetter(args[0].charAt(args[0].length() - 1))) {
            numWords++;
        }

        System.out.println(args[0]);
        System.out.println("Char count: " + numChars);
        System.out.println("Word count: " + numWords);
    }
}
