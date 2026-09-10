package savages;

/** Dining savages solved with a monitor: one cook, ten savages, a pot of five servings. */
public class Main {

    public static void main(String[] args) {
        Pot pot = new Pot(5);
        new Cook(pot).start();
        for (int i = 0; i < 10; i++) {
            new Savage(i, pot).start();
        }
    }
}
