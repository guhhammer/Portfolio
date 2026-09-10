package realestate;

/** A property with a price that appreciates or depreciates by a percentage. */
public abstract class Property {

    protected double price;

    public Property(double price) { this.price = price; }

    public double appreciate(double percent) {
        price = price * (1.0 + percent / 100);
        return price;
    }

    public double depreciate(double percent) {
        price = price * (1.0 - percent / 100);
        return price;
    }
}
