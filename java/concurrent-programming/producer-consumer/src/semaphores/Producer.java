package semaphores;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer extends Thread {

    private final int[] buffer;
    private final Semaphore mutex, items, slots;

    public Producer(int[] buffer, Semaphore mutex, Semaphore items, Semaphore slots) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.items = items;
        this.slots = slots;
    }

    @Override
    public void run() {
        Random generator = new Random();
        while (true) {
            try {
                int k = generator.nextInt(1000);
                slots.acquire();
                mutex.acquire();
                buffer[Main.last] = k;
                Main.last = (Main.last + 1) % 100;
                System.out.println("\nProduced: " + k);
                mutex.release();
                items.release();
                Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
