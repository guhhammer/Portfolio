package fifo;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** FIFO barbershop: customers are served strictly in arrival order, each waiting on its own ticket semaphore
 *  held in a queue. Concurrent Programming course, PUCPR (2020). */
public class Barbershop {

    public static final int T_MIN = 1000;
    public static final int T_MAX = 3000;
    public static final int INTERVAL = T_MAX - T_MIN;
    private static final Random period = new Random();

    public static final int DELAY = 4;          // a haircut takes DELAY times longer than a customer action
    public static final int CAPACITY = 4;
    public static int customerCount = 0;

    public static final SemaphoreQueue queue = new SemaphoreQueue(CAPACITY);

    public static void main(String[] args) throws InterruptedException {
        int customers = 15;

        Semaphore mutex = new Semaphore(1),
                  customer = new Semaphore(0),
                  customerDone = new Semaphore(0),
                  barberDone = new Semaphore(0);

        new Barber(mutex, customer, customerDone, barberDone).start();

        for (int i = 0; i < customers; i++) {
            Customer c = new Customer(i, mutex, customer, customerDone, barberDone);
            Thread.sleep(T_MIN + period.nextInt(INTERVAL));
            c.start();
            Thread.sleep(T_MIN);
        }
    }
}
