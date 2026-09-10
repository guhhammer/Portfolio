/** Times series that approximate pi with one billion terms each, comparing float loops against a double reference.
 *  Computer Architecture course, PUCPR (2020). */
public class PiSeries {

    private static float powerOf(int x, int y) { return (float) Math.pow(x, y); }

    /** One term of Bellard's BBP-type formula. */
    public static float bellardTerm(int k) {
        return (1 / powerOf(16, k)) * ((4.0f / (8.0f * k + 1)) - (2.0f / (8.0f * k + 4))
                - (1.0f / (8.0f * k + 5)) - (1.0f / (8.0f * k + 6)));
    }

    public static float bellard(int terms) {
        float sum = 0.0f;
        for (int k = 0; k < terms; k++) { sum += bellardTerm(k); }
        return sum;
    }

    /** One term of the Nilakantha series. */
    public static float nilakanthaTerm(int k) {
        return (((k % 2 == 0) ? 1 : -1) * 1.0f) / ((2.0f * k + 2) * (2.0f * k + 3) * (2.0f * k + 4));
    }

    public static float nilakantha(int terms) {
        float sum = 0.0f;
        for (int k = 0; k < terms; k++) { sum += nilakanthaTerm(k); }
        return 3 + 4 * sum;
    }

    /** Nilakantha with the term rewritten to fewer multiplications. */
    public static float nilakanthaFast(int terms) {
        float sum = 0.0f;
        for (int k = 0; k < terms; k++) {
            sum += (((k % 2 == 0) ? 1 : -1) * 1.0f) / (8.0f * (k + 1) * (k + 1.5f) * (k + 2.0f));
        }
        return 3 + 4 * sum;
    }

    /** The reference loop in double precision. */
    public static double reference(long terms) {
        double pi = 0.0, sign = 1.0, i2;
        for (long i = 0; i < terms; i++) {
            i2 = 2 * i;
            pi += sign / ((i2 + 2) * (i2 + 3) * (i2 + 4));
            sign = -sign;
        }
        return 3.0 + 4.0 * pi;
    }

    private static void time(String label, java.util.function.Supplier<Number> computation) {
        long begin = System.currentTimeMillis();
        Number pi = computation.get();
        long elapsed = System.currentTimeMillis() - begin;
        System.out.printf("%-16s pi = %.20f   (%d ms)%n", label, pi.doubleValue(), elapsed);
    }

    public static void main(String[] args) {
        int terms = 1_000_000_000;
        time("Nilakantha", () -> nilakantha(terms));
        time("Nilakantha fast", () -> nilakanthaFast(terms));
        time("Reference", () -> reference(terms));
    }
}
