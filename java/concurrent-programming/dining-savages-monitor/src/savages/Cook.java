package savages;

public class Cook extends Thread {

    private final Pot pot;

    public Cook(Pot pot) { this.pot = pot; }

    @Override
    public void run() {
        while (true) {
            System.out.println("*** Cook is ready to fill the pot ...");
            pot.fill();
            System.out.println("*** Cook has just filled the pot");
        }
    }
}
