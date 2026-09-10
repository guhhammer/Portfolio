package parallelism;

import java.util.Random;

/** CPU-bound work unit: sums the square roots of `sequenceSize` random integers. */
public class Calculation {

    private final long sequenceSize;

    public Calculation(long sequenceSize) { this.sequenceSize = sequenceSize; }

    public double execute() {
        Random generator = new Random();
        double total = 0.0;
        for (long i = 0; i < sequenceSize; i++) {
            total += Math.sqrt(generator.nextInt(10000));
        }
        return total;
    }
}
