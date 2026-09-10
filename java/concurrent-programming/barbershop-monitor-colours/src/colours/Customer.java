package colours;

import java.util.Random;

public class Customer extends Thread {

    private static final int T_MIN = 1000, T_MAX = 4000, INTERVAL = T_MAX - T_MIN;
    private static final Random period = new Random();

    private final int id;
    private final Barbershop shop;
    private CustomerColour colour = CustomerColour.GREEN;

    public Customer(int id, Barbershop shop) {
        this.id = id;
        this.shop = shop;
    }

    public int getCustomerId() { return id; }

    public void setColour(CustomerColour colour) { this.colour = colour; }

    public CustomerColour getColour() { return colour; }

    private static void spendTime() throws InterruptedException {
        Thread.sleep(T_MIN + period.nextInt(INTERVAL));
    }

    @Override
    public void run() {
        while (true) {
            try {
                shop.getHaircut(this);
                spendTime();
                switch (colour) {
                    case BLUE:
                        spendTime();
                        spendTime();
                        spendTime();
                        break;
                    default:
                        spendTime();
                        break;
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
        }
    }
}
