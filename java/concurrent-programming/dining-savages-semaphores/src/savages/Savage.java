package savages;

import static savages.Tribe.INTERVAL;
import static savages.Tribe.POT_CAPACITY;
import static savages.Tribe.T_MIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Savage extends Thread {

    private final int id;
    private final Semaphore mutex, potEmpty, potFull;
    private final Random period = new Random();
    private final int REST = INTERVAL / 2;   // rest after a meal (delay)

    public Savage(int id, Semaphore mutex, Semaphore potEmpty, Semaphore potFull) {
        this.id = id;
        this.mutex = mutex;
        this.potEmpty = potEmpty;
        this.potFull = potFull;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mutex.acquire();
                if (Tribe.servingsAvailable == 0) {
                    potEmpty.release();                 // wake the cook
                    potFull.acquire();                  // wait for the refill
                    Tribe.servingsAvailable = POT_CAPACITY;
                }
                takeFromPot();
                mutex.release();
                eat();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public void takeFromPot() throws InterruptedException {
        Tribe.servingsAvailable -= 1;
        Thread.sleep(T_MIN + period.nextInt(INTERVAL) + REST);
    }

    public void eat() throws InterruptedException {
        System.out.println("Savage " + id + " took a serving. (servings left: " + Tribe.servingsAvailable + ")");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL) + REST);
    }
}
