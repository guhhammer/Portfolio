package barbershop;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** Sleeping-barber problem with semaphores: customers arrive at random intervals, the waiting room holds
 *  CAPACITY customers and anyone who finds it full gives up. Concurrent Programming course, PUCPR (2020). */
public class Barbershop {

    public static final int T_MIN = 1000;               // minimum delay (ms)
    public static final int T_MAX = 1500;               // maximum delay (ms)
    public static final int INTERVAL = T_MAX - T_MIN;

    public static final int CAPACITY = 4;
    public static int customerCount = 0;

    public static void main(String[] args) throws InterruptedException {
        int customers = 10;
        Random period = new Random();

        Semaphore mutex = new Semaphore(1);
        Semaphore customer = new Semaphore(0);
        Semaphore barber = new Semaphore(0);
        Semaphore customerDone = new Semaphore(0);
        Semaphore barberDone = new Semaphore(0);

        new Barber(customer, barber, customerDone, barberDone).start();

        for (int i = 0; i < customers; i++) {
            Customer c = new Customer(i, mutex, customer, barber, customerDone, barberDone);
            Thread.sleep(T_MIN + period.nextInt(INTERVAL));
            c.start();
        }
    }
}
