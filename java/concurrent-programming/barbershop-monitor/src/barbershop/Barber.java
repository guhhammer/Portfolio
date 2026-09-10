package barbershop;

import java.util.Random;

public class Barber extends Thread {

    private final Barbershop shop;

    public Barber(Barbershop shop) { this.shop = shop; }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(1000));
                shop.getNextCustomer();
            } catch (InterruptedException ex) { ex.printStackTrace(); }

            System.out.println("Barber is cutting...\n");

            try {
                shop.finishHaircut();
                Thread.sleep(new Random().nextInt(3000));
            } catch (InterruptedException ex) { ex.printStackTrace(); }

            System.out.println("Barber finished cutting.\n");
        }
    }
}
