package realestate;

/** A house appreciates at half the given rate. */
public class House extends Property {

    public House(double price) { super(price); }

    @Override
    public double appreciate(double percent) {
        price = price * (1.0 + percent / (100 * 2));
        return price;
    }
}
