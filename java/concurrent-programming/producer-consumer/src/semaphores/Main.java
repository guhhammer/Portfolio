package semaphores;

import java.util.concurrent.Semaphore;

/** Producer-consumer with semaphores: a 100-slot ring buffer guarded by a mutex plus counting semaphores
 *  for the items and free slots; two producers and two consumers. Concurrent Programming course, PUCPR (2020). */
public class Main {

    static int first = 0;
    static int last = 0;

    public static void main(String[] args) {
        int[] buffer = new int[100];

        Semaphore mutex = new Semaphore(1);
        Semaphore items = new Semaphore(0);
        Semaphore slots = new Semaphore(100);

        new Consumer(buffer, mutex, items, slots).start();
        new Consumer(buffer, mutex, items, slots).start();
        new Producer(buffer, mutex, items, slots).start();
        new Producer(buffer, mutex, items, slots).start();
    }
}
