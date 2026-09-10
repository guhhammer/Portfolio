package zeta;

/** Approximates the Riemann zeta function by its series: zeta(a) = sum of 1/i^a. */
public class ZetaSeries {

    public static void zeta(int a, int terms) {
        float sum = 0;
        for (int i = 1; i < terms; i++) {
            double term = Math.pow(i, a);
            sum += 1 / term;
            System.out.println("i^a: " + term + "\nsum: " + sum + "\n\\\\\\");
        }
        System.out.println("Zeta(" + a + ") is approximately: " + sum);
    }

    public static void main(String[] args) { zeta(2, 1000); }
}
