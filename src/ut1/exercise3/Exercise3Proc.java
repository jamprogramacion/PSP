package ut1.exercise3;

/**
 * Takes two int numbers and outs the sum of all int from the smaller to the greater.
 */
public class Exercise3Proc {
    public static void main(String[] args) {
        int num1;
        int num2;

        /* Try to convert two first arguments to int, and return if error. */
        try {
            num1 = Integer.parseInt(args[0]);
            num2 = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Argument not an integer.");

            return;
        }

        /* num1 has to be the smaller and num2 the greater */
        if (num2 < num1) {
            int aux = num1;
            num1 = num2;
            num2 = aux;
        }
        /* Loops num1 and sums it in each iteration until num1 == num2 */
        float sumSeries = 0;
        while (num1 <= num2) {
            sumSeries += num1;
            num1++;
        }

        System.out.println("Sum from " + args[0] + " to " + args[1] + " = " + sumSeries);
    }
}
