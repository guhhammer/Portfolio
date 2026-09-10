package colours;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** The barbershop as a monitor whose behaviour follows a colour-coded state diagram: customers go
 *  GREEN (resting) -> YELLOW (arrived) -> RED (in the chair) / ORANGE (waiting on a chair) / BLUE (shop full),
 *  the barber PURPLE (resting) -> LIGHT_PINK (cutting) -> DARK_PINK (finishing and charging), with four
 *  condition gates between them. */
public class Barbershop {

    private static final int T_MIN = 1000;
    private static final int T_MAX = 4000;
    private static final int INTERVAL = T_MAX - T_MIN;
    private static final Random period = new Random();

    private final int chairs;
    private int orangeSeats;
    private boolean redCustomer = false;

    private final Lock gateM = new ReentrantLock();
    private final Condition gateA = gateM.newCondition(),
                            gateB = gateM.newCondition(),
                            gateC = gateM.newCondition(),
                            gateD = gateM.newCondition(),
                            chairOccupied = gateM.newCondition();

    public Barbershop(int chairs) {
        this.chairs = chairs;
        this.orangeSeats = chairs;
    }

    public void getHaircut(Customer customer) throws InterruptedException {
        gateM.lock();
        try {
            System.out.printf("Customer enters the barbershop:%n\t id: %d  |  colour: %s.%n\t", customer.getCustomerId(), customer.getColour());

            switch (customer.getColour()) {
                case GREEN:
                    customer.setColour(CustomerColour.YELLOW);
                    System.out.println("=> customer turns YELLOW.\n");
                    break;

                case YELLOW:
                    if (!redCustomer && orangeSeats == chairs) {
                        gateA.signal();
                        chairOccupied.await();
                        customer.setColour(CustomerColour.RED);
                        redCustomer = true;
                        System.out.println("=> customer turns RED (gate A: signal; gate B: await).\n");
                        gateB.await();
                    } else if (orangeSeats > 0) {
                        customer.setColour(CustomerColour.ORANGE);
                        System.out.println("=> customer turns ORANGE (gate C: await).\n");
                        orangeSeats--;
                        gateC.await();
                    } else {
                        customer.setColour(CustomerColour.BLUE);
                        System.out.println("=> customer turns BLUE.");
                    }
                    break;

                case RED:
                    chairOccupied.signal();
                    redCustomer = false;
                    System.out.println("customer turns GREEN (gate D: signal)");
                    customer.setColour(CustomerColour.GREEN);
                    gateD.signal();
                    break;

                case ORANGE:
                    if (!redCustomer) {
                        gateA.signal();
                        chairOccupied.await();
                        orangeSeats++;
                        System.out.println("customer turns RED (gate A: signal; gate B: await)");
                        customer.setColour(CustomerColour.RED);
                        redCustomer = true;
                        gateB.await();
                    }
                    break;

                case BLUE:
                    customer.setColour(CustomerColour.YELLOW);
                    Thread.sleep(T_MIN + period.nextInt(INTERVAL));
                    System.out.println("customer turns YELLOW");
                    break;
            }
        } finally {
            gateM.unlock();
        }
    }

    public void getNextCustomer(Barber barber) throws InterruptedException {
        gateM.lock();
        try {
            switch (barber.getColour()) {
                case DARK_PINK:
                    gateB.signal();
                    System.out.println("\nbarber is finishing the haircut...\n");
                    System.out.println("\nbarber is charging for the haircut...\n");
                    finishHaircut();
                    gateD.await();
                    System.out.println("\nbarber turns PURPLE.\n");
                    barber.setColour(BarberColour.PURPLE);
                    break;

                case LIGHT_PINK:
                    System.out.println("\nbarber is cutting hair...\n");
                    Thread.sleep(T_MIN + period.nextInt(INTERVAL));
                    System.out.println("\nbarber turns DARK_PINK.\n");
                    barber.setColour(BarberColour.DARK_PINK);
                    break;

                case PURPLE:
                    gateC.signal();
                    System.out.println("\nbarber is resting now...\n");
                    gateA.await();
                    System.out.println("\nbarber turns LIGHT_PINK.\n");
                    barber.setColour(BarberColour.LIGHT_PINK);
                    break;
            }
        } finally {
            gateM.unlock();
        }
    }

    public void finishHaircut() throws InterruptedException {
        gateM.lock();
        try {
            Thread.sleep(T_MIN + period.nextInt(INTERVAL));
            System.out.println("\nHaircut finished!\n");
        } finally {
            gateM.unlock();
        }
    }
}
