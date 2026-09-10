package barbershop;

import java.util.Random;

public class Customer extends Thread {

    private final int id;
    private final Barbershop shop;

    public Customer(Barbershop shop, int id) {
        this.shop = shop;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            shop.getHaircut();
            System.out.println("Customer " + id + " got a haircut.");
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException ex) { ex.printStackTrace(); }
    }
}
