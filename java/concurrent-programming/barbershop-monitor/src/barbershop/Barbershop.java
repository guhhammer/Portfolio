package barbershop;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Barbershop as a monitor (ReentrantLock + conditions) instead of bare semaphores. */
public class Barbershop {

    private static int barbers = 0, chairs = 0, open = 0;

    private final Lock mutex = new ReentrantLock();
    private final Condition barberAvailable = mutex.newCondition(),
                            chairOccupied = mutex.newCondition(),
                            doorOpen = mutex.newCondition(),
                            customerLeft = mutex.newCondition();

    /** Called by a customer: waits for a barber, sits down, waits for the door to open, leaves. */
    public void getHaircut() throws InterruptedException {
        mutex.lock();
        try {
            while (barbers == 0) { barberAvailable.await(); }
            barbers--;
            chairs++;
            chairOccupied.signal();
            while (open == 0) { doorOpen.await(); }
            open--;
            customerLeft.signal();
        } finally {
            mutex.unlock();
        }
    }

    /** Called by the barber: announces availability and waits for a customer in the chair. */
    public void getNextCustomer() throws InterruptedException {
        mutex.lock();
        try {
            barbers++;
            barberAvailable.signal();
            while (chairs == 0) { chairOccupied.await(); }
            chairs--;
        } finally {
            mutex.unlock();
        }
    }

    /** Called by the barber: opens the door and waits for the customer to leave. */
    public void finishHaircut() throws InterruptedException {
        mutex.lock();
        try {
            open++;
            doorOpen.signal();
            while (open > 0) { customerLeft.await(); }
        } finally {
            mutex.unlock();
        }
    }
}
