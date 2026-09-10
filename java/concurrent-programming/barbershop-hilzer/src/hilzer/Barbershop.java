package hilzer;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** Hilzer's barbershop: three barbers, three chairs, a sofa for four, and a standing room for the rest.
 *  Customers are served in arrival order through two ticket queues (sofa and chairs) and pay before leaving.
 *  Concurrent Programming course, PUCPR (2020). */
public class Barbershop {

    public static final int T_MIN = 1000;   // minimum delay (ms)
    public static final int T_MAX = 3000;   // maximum delay (ms)
    public static final int INTERVAL = T_MAX - T_MIN;
    private static final Random period = new Random();

    public static final int DELAY = 5;      // how much slower a haircut is than a customer action

    public static final int BARBERS = 3;
    public static final int CHAIRS = BARBERS;
    public static final int SOFA_SEATS = 4;
    public static final int STANDING_ROOM = 13;
    public static final int TOTAL_CAPACITY = CHAIRS + SOFA_SEATS + STANDING_ROOM;

    public static int customerCount = 0;

    public static final SemaphoreQueue sofaQueue = new SemaphoreQueue(SOFA_SEATS);
    public static final SemaphoreQueue chairQueue = new SemaphoreQueue(CHAIRS);

    // Note: with a large DELAY the barbers fall behind, the queues stop shrinking and late customers starve.

    public static void main(String[] args) throws InterruptedException {
        int customers = 50;

        Semaphore mutex = new Semaphore(1),
                  barber = new Semaphore(0),
                  sofa = new Semaphore(SOFA_SEATS),
                  customerInSofaQueue = new Semaphore(0),
                  customerInChairQueue = new Semaphore(0),
                  payment = new Semaphore(0),
                  receipt = new Semaphore(0);

        for (int i = 0; i < BARBERS; i++) {
            new Barber(i, mutex, barber, customerInSofaQueue, customerInChairQueue, payment, receipt).start();
            Thread.sleep(T_MIN);
        }

        for (int i = 0; i < customers; i++) {
            Customer c = new Customer(i, mutex, sofa, barber, customerInSofaQueue, customerInChairQueue, payment, receipt);
            Thread.sleep(T_MIN + period.nextInt(INTERVAL));
            c.start();
            Thread.sleep(T_MIN);
        }
    }
}
