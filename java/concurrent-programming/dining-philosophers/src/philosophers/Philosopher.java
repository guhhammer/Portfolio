package philosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** Thinks, picks up both forks, eats, puts them back. A limiter semaphore admitting at most N-1 philosophers
 *  to the table at once prevents the circular wait that would deadlock the ring. */
public class Philosopher extends Thread {

    private final int id;
    private static final int T_MIN = 1000;   // ms
    private static final int T_MAX = 5000;   // ms
    private static final int INTERVAL = T_MAX - T_MIN;
    private final Random period = new Random();

    private final Semaphore rightFork, leftFork, limiter;

    public Philosopher(int id, Semaphore rightFork, Semaphore leftFork, Semaphore limiter) {
        this.id = id;
        this.rightFork = rightFork;
        this.leftFork = leftFork;
        this.limiter = limiter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                think();
                pickUpForks();
                eat();
                putDownForks();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void think() throws InterruptedException {
        Thread.sleep(T_MIN + period.nextInt(INTERVAL));
    }

    private void eat() throws InterruptedException {
        int time = T_MIN + period.nextInt(INTERVAL);
        System.out.println("Philosopher " + id + " eating for " + time + " ms ...");
        Thread.sleep(time);
        System.out.println("Philosopher " + id + " finished eating.");
    }

    private void pickUpForks() throws InterruptedException {
        limiter.acquire();
        rightFork.acquire();
        leftFork.acquire();
    }

    private void putDownForks() {
        rightFork.release();
        leftFork.release();
        limiter.release();
    }
}
