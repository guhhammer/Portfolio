package fifo;

import static fifo.Barbershop.DELAY;
import static fifo.Barbershop.INTERVAL;
import static fifo.Barbershop.T_MIN;
import static fifo.Barbershop.queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barber extends Thread {

    private final Semaphore mutex, customer, customerDone, barberDone;
    private final Random period = new Random();

    public Barber(Semaphore mutex, Semaphore customer, Semaphore customerDone, Semaphore barberDone) {
        this.mutex = mutex;
        this.customer = customer;
        this.customerDone = customerDone;
        this.barberDone = barberDone;
    }

    @Override
    public void run() {
        while (true) {
            try {
                customer.acquire();
                mutex.acquire();
                Semaphore ticket = queue.remove();   // the customer who has waited longest
                mutex.release();

                cutHair();
                if (ticket != null) { ticket.release(); }

                customerDone.acquire();
                barberDone.release();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void cutHair() throws InterruptedException {
        System.out.println("The barber is cutting hair ...");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL) * DELAY);
    }
}
