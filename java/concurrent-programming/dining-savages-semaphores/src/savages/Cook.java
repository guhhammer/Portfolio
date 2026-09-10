package savages;

import static savages.Tribe.INTERVAL;
import static savages.Tribe.T_MIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cook extends Thread {

    private final Semaphore potEmpty, potFull;
    private final Random period = new Random();

    public Cook(Semaphore potEmpty, Semaphore potFull) {
        this.potEmpty = potEmpty;
        this.potFull = potFull;
    }

    @Override
    public void run() {
        try {
            while (true) {
                potEmpty.acquire();   // wait until a savage reports the pot empty
                fillPot();
                potFull.release();    // tell the savage the pot is full again
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void fillPot() throws InterruptedException {
        Thread.sleep(T_MIN + period.nextInt(INTERVAL));
        System.out.println("\nCook filled the pot.\n");
    }
}
