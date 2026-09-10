package savages;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** Dining-savages problem with semaphores: a tribe eats from a shared pot; when it is empty the savage who
 *  finds it empty wakes the cook, who refills it. Concurrent Programming course, PUCPR (2020). */
public class Tribe {

    public static final int T_MIN = 1000;               // minimum delay (ms)
    public static final int T_MAX = 1010;               // maximum delay (ms)
    public static final int INTERVAL = T_MAX - T_MIN;

    public static int servingsAvailable = 0;
    public static final int POT_CAPACITY = 50;          // servings the cook puts in the pot

    public static void main(String[] args) throws InterruptedException {
        int population = 100;
        Random period = new Random();

        Semaphore mutex = new Semaphore(1);
        Semaphore potEmpty = new Semaphore(0);
        Semaphore potFull = new Semaphore(0);

        new Cook(potEmpty, potFull).start();

        for (int i = 0; i < population; i++) {
            Savage s = new Savage(i, mutex, potEmpty, potFull);
            Thread.sleep(T_MIN + period.nextInt(INTERVAL));
            s.start();
        }
    }
}
