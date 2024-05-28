package examen.ejercicio2;

import examen.Utils;

class Cash {
    private int totalCashOrange;
    private int totalCashLemon;

    public Cash() {
        totalCashOrange = 0;
        totalCashLemon = 0;
    }

    public synchronized int payOrange(int amount) {
        totalCashOrange += amount;

        return totalCashOrange;
    }

    public synchronized int payLemon(int amount) {
        totalCashLemon += amount;

        return totalCashLemon;
    }

    public int getTotalCashOrange() {
        return totalCashOrange;
    }

    public int getTotalCashLemon() {
        return totalCashLemon;
    }
}

class Person extends Thread {
    private final int orange;
    private final int lemon;
    private final int personNumber;
    private final Cash cash;

    public Person(int personNumber, int orange, int lemon, Cash cash) {
        super(String.valueOf(personNumber + 1));

        this.orange = orange;
        this.lemon = lemon;
        this.personNumber = personNumber;
        this.cash = cash;
    }

    @Override
    public void run() {
        final int MAX_UNITS = 10;
        final long TIME_PAY = 1;
        final int PRODUCT_NUMBER= Utils.randomInt(0, 1);
        final int COST = PRODUCT_NUMBER == 0 ? orange : lemon;
        final String PRODUCT = PRODUCT_NUMBER == 0 ? "naranjas" : "limones";
        final int UNITS = Utils.randomInt(1, MAX_UNITS);
        final int TOTAL = COST * UNITS;

        super.run();

        System.out.println("La persona [" + personNumber + "] llega al puesto");
        try {
            if (Utils.randomInt(0, 1) == 1) {
                sleep(1000);
            }
            System.out.println("La persona [" + personNumber + "] decide comprar " + PRODUCT);
            sleep(TIME_PAY * 1000);
            if (PRODUCT_NUMBER == 0) {
                cash.payOrange(TOTAL);
            } else {
                cash.payLemon(TOTAL);
            }
            System.out.println("La persona [" + personNumber + "] compra " + UNITS + " " + PRODUCT + " y paga " + TOTAL + " Euros");
        } catch (InterruptedException e) {
            System.out.println("Excepción en la persona [" + personNumber + "]: " + e.getMessage());
        }
    }
}

public class Ejercicio2 {
    public static void main (String[] args) {
        final long INTERVAL = 2;
        final int NUM_PERSONS = 20;
        final int ORANGE = 2;
        final int LEMON = 1;
        final Cash cash = new Cash();
        final Person[] persons = new Person[NUM_PERSONS];

        for (int numPerson = 0; numPerson < persons.length; numPerson++) {
            try {
                Thread.sleep(INTERVAL * 1000);
                persons[numPerson] = new Person(numPerson + 1, ORANGE, LEMON, cash);
                persons[numPerson].start();
            } catch (InterruptedException e) {
                System.out.println("Exception en la persona [" + (numPerson + 1) + "] hilo principal: " + e.getMessage());
            }
        }

        while (Utils.threadsActive(persons)) {

        }

        System.out.println("------------------------------");
        System.out.println("Se han gastado " + cash.getTotalCashOrange() + " Euros en naranjas, y " + cash.getTotalCashLemon() +" Euros en limones");
    }
}
