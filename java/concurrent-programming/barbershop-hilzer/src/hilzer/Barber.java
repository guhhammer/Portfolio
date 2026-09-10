package hilzer;

import static hilzer.Barbershop.DELAY;
import static hilzer.Barbershop.INTERVAL;
import static hilzer.Barbershop.T_MIN;
import static hilzer.Barbershop.chairQueue;
import static hilzer.Barbershop.sofaQueue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barber extends Thread {

    private final int id;
    private final Semaphore mutex, barber, customerInSofaQueue, customerInChairQueue, payment, receipt;
    private final Random period = new Random();

    public Barber(int id, Semaphore mutex, Semaphore barber, Semaphore customerInSofaQueue,
                  Semaphore customerInChairQueue, Semaphore payment, Semaphore receipt) {
        this.id = id;
        this.mutex = mutex;
        this.barber = barber;
        this.customerInSofaQueue = customerInSofaQueue;
        this.customerInChairQueue = customerInChairQueue;
        this.payment = payment;
        this.receipt = receipt;
    }

    private static void wake(Semaphore ticket) {
        if (ticket != null) { ticket.release(); }   // remove() returns null when the queue is empty
    }

    @Override
    public void run() {
        while (true) {
            try {
                customerInSofaQueue.acquire();    // a customer is waiting to move to the sofa
                mutex.acquire();
                Semaphore sofaTicket = sofaQueue.remove();
                mutex.release();
                wake(sofaTicket);

                customerInChairQueue.acquire();   // a customer is waiting for a chair
                mutex.acquire();
                Semaphore chairTicket = chairQueue.remove();
                mutex.release();
                wake(chairTicket);

                barber.release();
                cutHair();

                payment.acquire();
                receipt.release();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void cutHair() throws InterruptedException {
        System.out.println("Barber " + id + " is cutting hair ...");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL) * DELAY);
    }
}
