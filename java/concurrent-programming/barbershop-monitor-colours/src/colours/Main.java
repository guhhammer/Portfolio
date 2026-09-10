package colours;

/** One barber, four chairs, eight customers arriving every four seconds. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static final int CHAIRS = 4;
    public static final int CUSTOMERS = 8;

    public static void main(String[] args) throws InterruptedException {
        Barbershop shop = new Barbershop(CHAIRS);
        new Barber(shop).start();

        for (int i = 0; i < CUSTOMERS; i++) {
            Thread.sleep(2000);
            new Customer(i, shop).start();
            Thread.sleep(2000);
        }
    }
}
