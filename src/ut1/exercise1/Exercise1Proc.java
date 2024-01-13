package ut1.exercise1;

/**
 * Takes two int numbers and outs a series with all int from the smaller to the greater.
 */
public class Exercise1Proc {
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
        /* Create series from num1 to num2, printing and increasing num1 */
        StringBuilder series = new StringBuilder();
        while (num1 <= num2) {
            series.append(num1);
            if (num1 < num2) {
                series.append(" ,");
            }
            num1++;
        }

        System.out.println(series);
    }
}
