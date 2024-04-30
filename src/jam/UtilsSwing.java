/**
 *
 * @author Jesús Avilés Martínez
 *
 */
package jam;

import javax.swing.JOptionPane;

/**
 * Swing utilities that makes life easier.
 */
public class UtilsSwing {

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
}
