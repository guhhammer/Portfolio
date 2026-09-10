package progression;

/** Prints n/(n+1) as it climbs from 0 towards 1, stopping when float precision makes it equal to 1. */
public class FractionProgression {

    public static void progression() {
        float numerator = 0, denominator = 1;
        boolean running = true;
        do {
            System.out.println("( " + numerator + " / " + denominator + " ) = " + numerator / denominator + "\n \\\\\\");
            numerator++;
            denominator++;
            if (numerator / denominator == 1) { running = false; }
        } while (running);
        System.out.println("End");
    }

    public static void main(String[] args) { progression(); }
}
