package colours;

public class Barber extends Thread {

    private final Barbershop shop;
    private BarberColour colour = BarberColour.PURPLE;

    public Barber(Barbershop shop) { this.shop = shop; }

    public void setColour(BarberColour colour) { this.colour = colour; }

    public BarberColour getColour() { return colour; }

    @Override
    public void run() {
        while (true) {
            try {
                shop.getNextCustomer(this);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
