package philosophers;

import java.util.concurrent.Semaphore;

/** Dining philosophers with semaphores: N philosophers around a table share N forks.
 *  To reproduce the deadlock, remove the limiter from pickUpForks/putDownForks and set the thinking time to
 *  0-1 ms: every philosopher grabs the right fork and waits forever for the left one.
 *  Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) {
        final int n = 6;                                  // philosophers and forks
        Semaphore limiter = new Semaphore(n - 1);

        Semaphore[] forks = new Semaphore[n];
        for (int i = 0; i < n; i++) { forks[i] = new Semaphore(1); }

        for (int i = 0; i < n; i++) {
            int right = i, left = (i + 1) % n;
            new Philosopher(i, forks[right], forks[left], limiter).start();
        }
    }
}
