/** Prints one million characters and reports how long console output takes. */
public class PrintBenchmark {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int x = 0; x < 1_000_000; x++) {
            System.out.print("x");
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("\n\n\t\t Time elapsed: " + elapsed + " milliseconds. \n");
    }
}
