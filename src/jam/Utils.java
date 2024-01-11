package jam;

import javax.swing.JOptionPane;

/**
 *
 * @author Jesús Avilés Martínez
 *
 */
public class Utils {

    /**
     *
     * @param message: mensaje que se mostrará al usuario.
     * @param error: mensaje de error que se mostrará en caso de que no se introduzca un número.
     * 		Si es null, se muestra un mensaje predefinido.
     * @return número entero introducido por el usuario.
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
     *
     * @param message: mensaje que se mostrará al usuario.
     * @param error: mensaje de error que se mostrará en caso de que no se introduzca un número.
     * 		Si es null, se muestra un mensaje predefinido.
     * @return número doble introducido por el usuario.
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
     *
     * @param min: número mínimo que se quiere obtener, incluido.
     * @param max: número máximo que se quiere obtener, excluido.
     * @return número aleatorio x tal que minimo <= x < maximo.
     */
    public static double randomNumber(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    /**
     *
     * @param min: número entero mínimo que se quiere obtener, incluido.
     * @param max: número entero máximo que se quiere obtener, excluido.
     * @return número entero aleatorio x tal que minimo <= x < maximo.
     */
    public static int randomInt(double min, double max) {
        return (int)(randomNumber(min, max));
    }
}
