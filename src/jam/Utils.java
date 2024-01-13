package jam;

import javax.swing.JOptionPane;

/**
 *
 * @author Jesús Avilés Martínez
 *
 */
public class Utils {

    /**
     * Shows input dialog until user enters an int.
     *
     * @param message: message to show to user.
     * @param error: message to show if not entering an int.
     * 		If null, shows a predefined message.
     * @return int entered by user.
     */
    public static int inputInt(String message, String error) {
        do {
            try {
                return Integer.parseInt(JOptionPane.showInputDialog(message));
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, error != null ? error : "Please, enter an integer");
            }
        } while (true);
    }

    /**
     * Shows input dialog until user enters a double.
     *
     * @param message: message to show to user.
     * @param error: message to show if not entering a double.
     * 		If null, shows a predefined message.
     * @return double entered by user.
     */
    public static double inputDouble(String message, String error) {
        do {
            try {
                return Double.parseDouble(JOptionPane.showInputDialog(message));
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, error != null ? error : "Please, enter a number");
            }
        } while (true);
    }

    /**
     * Returns a random number within interval [min, max).
     *
     * @param min: min number of random interval, included.
     * @param max: max number of random interval, not included.
     * @return random number x with min <= x < max.
     */
    public static double randomNumber(double min, double max) {

        return Math.random() * (max - min) + min;
    }

    /**
     * Returns a random int within interval [min, max).
     *
     * @param min: min int number of random interval, included.
     * @param max: max int number of random interval, not included.
     * @return random int number n with min <= n < max.
     */
    public static int randomInt(double min, double max) {

        return (int)(randomNumber(min, max));
    }
}
