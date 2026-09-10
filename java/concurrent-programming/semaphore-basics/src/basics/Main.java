package basics;

import java.util.concurrent.Semaphore;

/** The first semaphore exercises: wait/signal on one thread, a helper that acquires and releases,
 *  two tasks running freely, and ten tasks serialised by a mutex. Concurrent Programming course, PUCPR (2020). */
public class Main {

    static void waitAndSignal() throws InterruptedException {
        Semaphore s = new Semaphore(0);
        System.out.println("waiting ...");
        s.release();
        s.acquire();                 // WAIT
        System.out.println("acquired");
        s.release();                 // SIGNAL
        System.out.println("released");
    }

    static void acquireRelease(Semaphore x) throws InterruptedException {
        System.out.println("waiting ...");
        x.acquire();
        System.out.println("acquired");
        x.release();
        System.out.println("released");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-- 1. wait and signal on one thread");
        waitAndSignal();

        System.out.println("-- 2. acquire and release through a helper");
        acquireRelease(new Semaphore(1));

        System.out.println("-- 3. two tasks running concurrently");
        Semaphore free = new Semaphore(1);
        Task a = new Task(1, free, false), b = new Task(2, free, false);
        a.start(); b.start(); a.join(); b.join();

        System.out.println("-- 4. ten tasks with mutual exclusion");
        Semaphore mutex = new Semaphore(1);
        Task[] tasks = new Task[10];
        for (int i = 0; i < tasks.length; i++) { tasks[i] = new Task(i, mutex, true); tasks[i].start(); }
        for (Task t : tasks) { t.join(); }
        System.out.println("end of the tests");
    }
}
