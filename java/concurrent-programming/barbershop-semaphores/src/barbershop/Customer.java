package barbershop;

import static barbershop.Barbershop.CAPACITY;
import static barbershop.Barbershop.INTERVAL;
import static barbershop.Barbershop.T_MIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customer extends Thread {

    private final int id;
    private final Random period = new Random();
    private final Semaphore mutex, customer, barber, customerDone, barberDone;

    public Customer(int id, Semaphore mutex, Semaphore customer, Semaphore barber,
                    Semaphore customerDone, Semaphore barberDone) {
        this.id = id;
        this.mutex = mutex;
        this.customer = customer;
        this.barber = barber;
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
            mutex.release();

            customer.release();      // announce arrival
            barber.acquire();        // wait for the barber

            getHaircut();

            customerDone.release();  // satisfied
            barberDone.acquire();    // wait for the barber to finish

            mutex.acquire();
            Barbershop.customerCount -= 1;
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void arrive() { System.out.println("\nCustomer " + id + " arrived.\n"); }

    public void giveUp() { System.out.println("\nCustomer " + id + " gave up.\n"); }

    public void getHaircut() throws InterruptedException {
        System.out.println("Customer " + id + " is having a haircut ...");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL));
        System.out.println("Customer " + id + " got a haircut.");
    }
}
