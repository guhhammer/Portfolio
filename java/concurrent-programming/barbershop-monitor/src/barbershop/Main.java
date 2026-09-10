package barbershop;

/** One barber, thirty customers. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) {
        Barbershop shop = new Barbershop();
        new Barber(shop).start();
        for (int i = 0; i < 30; i++) {
            new Customer(shop, i).start();
        }
    }
}
