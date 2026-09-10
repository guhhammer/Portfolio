package semaphores;

import java.util.concurrent.Semaphore;

public class Consumer extends Thread {

    private final int[] buffer;
    private final Semaphore mutex, items, slots;

    public Consumer(int[] buffer, Semaphore mutex, Semaphore items, Semaphore slots) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.items = items;
        this.slots = slots;
    }

    @Override
    public void run() {
        while (true) {
            try {
                items.acquire();
                mutex.acquire();
                int k = buffer[Main.first];
                Main.first = (Main.first + 1) % 100;
                System.out.println("Consumed: " + k);
                mutex.release();
                slots.release();
                Thread.sleep(3000);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
