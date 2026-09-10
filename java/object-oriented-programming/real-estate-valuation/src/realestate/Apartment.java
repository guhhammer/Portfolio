package realestate;

/** An apartment depreciates at half the given rate. */
public class Apartment extends Property {

    public Apartment(double price) { super(price); }

    @Override
    public double depreciate(double percent) {
        price = price * (1.0 - percent / 100 / 2);
        return price;
    }
}
