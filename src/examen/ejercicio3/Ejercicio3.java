package examen.ejercicio3;

import examen.Utils;

import java.util.concurrent.Semaphore;

class Singer extends Thread {
    private final Semaphore dressRoom;

    public Singer(int singerNumber, Semaphore dressRoom) {
        super(String.valueOf(singerNumber + 1));

        this.dressRoom = dressRoom;
    }

    @Override
    public void run() {
        final int SING_TIME_MIN = 5;
        final int SING_TIME_MAX = 10;
        final long EAT_TIME = 3;
        final long SING_TIME = Utils.randomInt(SING_TIME_MIN, SING_TIME_MAX);

        super.run();

        while (true) {
            try {
                System.out.println("Cantante [" + getName() + "] intenta comer...");
                boolean canEat = dressRoom.tryAcquire(1);
                if (canEat) {
                    System.out.println("Cantante [" + getName() + "] consigue a comer");
                    Thread.sleep(EAT_TIME * 1000);
                    dressRoom.release(1);
                } else {
                    System.out.println("Cantante [" + getName() + "] NO PUEDE COMER");
                }
                System.out.println("Cantante [" + getName() + "] canta durante " + SING_TIME + " segundos...");
                Thread.sleep(SING_TIME * 1000);
            } catch (InterruptedException e) {
                System.out.println("Error en el cantante [" + getName() + "]");
            }
        }
    }
}

public class Ejercicio3 {
    public static void main(String[] args) {
        final int NUM_SINGERS = 3;
        final Semaphore dressRoom = new Semaphore(1);

        for (int singerNum = 0; singerNum < NUM_SINGERS; singerNum++) {
            Singer singer = new Singer(singerNum, dressRoom);
            singer.start();
        }
    }
}
