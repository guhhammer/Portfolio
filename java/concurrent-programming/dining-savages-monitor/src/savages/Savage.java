package savages;

import java.util.Random;

public class Savage extends Thread {

    private final int id;
    private final Pot pot;
    private Color color;

    public Savage(int id, Pot pot) {
        this.id = id;
        this.pot = pot;
        this.color = Color.GREEN;
    }

    public int getSavageId() { return id; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    @Override
    public void run() {
        Random r = new Random();
        while (true) {
            try {
                Thread.sleep(1000 + r.nextInt(4000));   // resting
            } catch (InterruptedException e) { e.printStackTrace(); }
            setColor(Color.YELLOW);
            pot.getServed(this);
            try {
                Thread.sleep(500 + r.nextInt(1000));    // eating
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
