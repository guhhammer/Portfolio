package hilzer;

import static hilzer.Barbershop.INTERVAL;
import static hilzer.Barbershop.T_MIN;
import static hilzer.Barbershop.TOTAL_CAPACITY;
import static hilzer.Barbershop.chairQueue;
import static hilzer.Barbershop.sofaQueue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer extends Thread {

    private final int id;
    private final Semaphore mutex, sofa, barber, customerInSofaQueue, customerInChairQueue, payment, receipt;
    private final Semaphore sofaTicket = new Semaphore(0);    // released by a barber when it is my turn for the sofa
    private final Semaphore chairTicket = new Semaphore(0);   // released by a barber when it is my turn for a chair
    private final Random period = new Random();

    public Customer(int id, Semaphore mutex, Semaphore sofa, Semaphore barber, Semaphore customerInSofaQueue,
                    Semaphore customerInChairQueue, Semaphore payment, Semaphore receipt) {
        this.id = id;
        this.mutex = mutex;
        this.sofa = sofa;
        this.barber = barber;
        this.customerInSofaQueue = customerInSofaQueue;
        this.customerInChairQueue = customerInChairQueue;
        this.payment = payment;
        this.receipt = receipt;
    }

    @Override
    public void run() {
        try {
            arrive();

            mutex.acquire();
            if (Barbershop.customerCount == TOTAL_CAPACITY) {   // shop full: leave
                mutex.release();
                giveUp();
                return;
            }
            Barbershop.customerCount += 1;
            sofaQueue.insert(sofaTicket);
            mutex.release();

            customerInSofaQueue.release();
            sofaTicket.acquire();

            sofa.acquire();                                       // sit on the sofa
            mutex.acquire();
            chairQueue.insert(chairTicket);
            mutex.release();
            customerInChairQueue.release();
            chairTicket.acquire();
            sofa.release();                                       // leave the sofa for the chair

            getHaircut();

            payment.release();
            receipt.acquire();

            mutex.acquire();
            Barbershop.customerCount -= 1;
            mutex.release();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private void arrive() { System.out.println("\nCustomer " + id + " arrived.\n"); }

    private void giveUp() { System.out.println("\nCustomer " + id + " gave up.\n"); }

    private void getHaircut() throws InterruptedException {
        System.out.println("Customer " + id + " is getting a haircut ...");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL));
        System.out.println("Customer " + id + " got a haircut.");
    }
}
