package ut1.exercise1;

public class Exercise1Proc {
    public static void main(String[] args) {
        int num1 = 0;
        int num2 = 0;

        try {
            num1 = Integer.parseInt(args[0]);
            num2 = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Argument not an integer.");

            return;
        }

        if (num2 < num1) {
            int aux = num1;
            num1 = num2;
            num2 = aux;
        }
        String series = "";
        while (num1 <= num2) {
            series += num1;
            if (num1 < num2) {
                series += " ,";
            }
            num1++;
        }

        System.out.println(series);
    }
}
