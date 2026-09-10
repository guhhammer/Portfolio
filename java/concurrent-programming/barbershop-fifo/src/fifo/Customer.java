package fifo;

import static fifo.Barbershop.CAPACITY;
import static fifo.Barbershop.INTERVAL;
import static fifo.Barbershop.T_MIN;
import static fifo.Barbershop.queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer extends Thread {

    private final int id;
    private final Semaphore mutex, customer, customerDone, barberDone;
    private final Semaphore ticket = new Semaphore(0);
    private final Random period = new Random();

    public Customer(int id, Semaphore mutex, Semaphore customer, Semaphore customerDone, Semaphore barberDone) {
        this.id = id;
        this.mutex = mutex;
        this.customer = customer;
        this.customerDone = customerDone;
        this.barberDone = barberDone;
    }

    @Override
    public void run() {
        try {
            arrive();

            mutex.acquire();
            if (Barbershop.customerCount == CAPACITY) {   // waiting room full: leave
                mutex.release();
                giveUp();
                return;
            }
            Barbershop.customerCount += 1;
            queue.insert(ticket);
            mutex.release();

            customer.release();
            ticket.acquire();        // wait for my turn

            getHaircut();

            customerDone.release();
            barberDone.acquire();

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
