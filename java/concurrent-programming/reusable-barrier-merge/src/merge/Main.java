package merge;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/** Reusable-barrier exercise: four workers each write a sorted file of random numbers, meet at a reusable
 *  (two-turnstile) barrier, hand the file to a combiner that merges the four files into one without duplicates,
 *  and start over. Files go to outputs/. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static final int NUMBER_COUNT = 1_000_000;         // numbers per worker file
    public static final int LIMIT = NUMBER_COUNT * 10;
    public static final int WAIT_MS = 4000;

    public static int counter = 0;
    public static final int WORKERS = 4;

    public static final Queue<String> fileQueue = new LinkedList<>();

    public static void main(String[] args) {
        Semaphore entryBarrier = new Semaphore(0),
                  exitBarrier = new Semaphore(1),
                  mutex = new Semaphore(1),
                  queueMutex = new Semaphore(1),
                  worker1 = new Semaphore(0),
                  worker2 = new Semaphore(0),
                  worker3 = new Semaphore(0),
                  worker4 = new Semaphore(0);

        new java.io.File("outputs").mkdirs();

        new Worker(1, "Thread1", mutex, queueMutex, worker1, entryBarrier, exitBarrier).start();
        new Worker(2, "Thread2", mutex, queueMutex, worker2, entryBarrier, exitBarrier).start();
        new Worker(3, "Thread3", mutex, queueMutex, worker3, entryBarrier, exitBarrier).start();
        new Worker(4, "Thread4", mutex, queueMutex, worker4, entryBarrier, exitBarrier).start();
        new Combiner(worker1, worker2, worker3, worker4).start();
    }
}
