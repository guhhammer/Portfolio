package barbershop;

import static barbershop.Barbershop.INTERVAL;
import static barbershop.Barbershop.T_MIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barber extends Thread {

    private final Random period = new Random();
    private final Semaphore customer, barber, customerDone, barberDone;
    public static final int DELAY = 8;

    public Barber(Semaphore customer, Semaphore barber, Semaphore customerDone, Semaphore barberDone) {
        this.customer = customer;
        this.barber = barber;
        this.customerDone = customerDone;
        this.barberDone = barberDone;
    }

    @Override
    public void run() {
        try {
            while (true) {
                customer.acquire();      // wait for a customer
                barber.release();        // signal that the barber is ready
                cutHair();
                customerDone.acquire();  // wait for the customer to be satisfied
                barberDone.release();    // signal that the haircut is finished
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cutHair() throws InterruptedException {
        System.out.println("\nBarber is cutting hair ...");
        Thread.sleep(T_MIN + period.nextInt(INTERVAL) * DELAY);
    }
}
