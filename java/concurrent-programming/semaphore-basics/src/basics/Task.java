package basics;

import java.util.concurrent.Semaphore;

/** A task that announces its start and end; with `exclusive` the semaphore serialises the tasks. */
public class Task extends Thread {

    private final int id;
    private final Semaphore mutex;
    private final boolean exclusive;

    public Task(int id, Semaphore mutex, boolean exclusive) {
        this.id = id;
        this.mutex = mutex;
        this.exclusive = exclusive;
    }

    @Override
    public void run() {
        try {
            if (exclusive) { mutex.acquire(); }
            System.out.println("Task " + id + " started");
            Thread.sleep(1000);
            System.out.println("Task " + id + " finished");
            if (exclusive) { mutex.release(); }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
