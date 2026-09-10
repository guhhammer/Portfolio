package matrix;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/** Measures the speed-up of multiplying two random matrices with one thread per processor versus sequentially.
 *  Each run writes the matrices and a results table to outputs/. Concurrent Programming course, PUCPR (2020).
 *  Note: with the small matrices of the exercise table the parallel gain is modest; larger bounds and sizes help. */
public class Main {

    private static int runNumber = 0;

    /** Multiplies A (aRows x aCols) by B (bRows x bCols) in parallel and sequentially; returns {parallelMs, sequentialMs}. */
    public static long[] execute(int aRows, int aCols, int bRows, int bCols, int boundA, int boundB) throws Exception {
        runNumber++;
        new File("outputs").mkdirs();

        Matrix a = new Matrix(aRows, aCols), b = new Matrix(bRows, bCols), c = new Matrix(aRows, bCols);
        a.setBound(boundA);
        b.setBound(boundB);
        a.fillRandom();
        b.fillRandom();
        a.setName("Matrix A");
        b.setName("Matrix B");
        c.setName("Matrix C (parallel)");

        // split the cells of C into one contiguous chunk per processor.
        int processors = Runtime.getRuntime().availableProcessors();
        List<Queue<int[]>> chunks = new ArrayList<>();
        for (int p = 0; p < processors; p++) { chunks.add(new LinkedList<>()); }
        int total = c.rows * c.cols, index = 0;
        for (int i = 0; i < c.rows; i++) {
            for (int j = 0; j < c.cols; j++) {
                chunks.get((int) ((long) index++ * processors / total)).offer(new int[]{i, j});
            }
        }

        Semaphore done = new Semaphore(0);
        long parallelStart = System.currentTimeMillis();
        for (Queue<int[]> chunk : chunks) { new Multiplier(a, b, c, chunk, done).start(); }
        done.acquire(processors);
        long parallelMs = System.currentTimeMillis() - parallelStart;

        long sequentialStart = System.currentTimeMillis();
        Matrix cs = Matrix.multiply(a, b);
        long sequentialMs = System.currentTimeMillis() - sequentialStart;
        cs.setName("Matrix C (sequential)");

        try (PrintWriter out = new PrintWriter(new File("outputs/run-" + runNumber + ".txt"), "UTF-8")) {
            a.write(out);
            b.write(out);
            c.write(out);
            cs.write(out);
        }
        System.out.printf("run %d: A %dx%d, B %dx%d, %d threads: parallel %d ms, sequential %d ms%n",
                runNumber, aRows, aCols, bRows, bCols, processors, parallelMs, sequentialMs);
        return new long[]{parallelMs, sequentialMs};
    }

    /** The four cases of the exercise table. */
    public static void definedValues() throws Exception {
        int[][] cases = { {100, 100, 100, 100}, {200, 200, 200, 200}, {100, 200, 200, 100}, {200, 400, 400, 200} };
        int processors = Runtime.getRuntime().availableProcessors();

        StringBuilder table = new StringBuilder();
        table.append("Speed-up test (bound = the limit of Random.nextInt(bound) used to fill A and B)\n\n");
        table.append("processors, m, k, n, bound, parallel ms (C), sequential ms (C), speed-up\n");
        for (int[] cs : cases) {
            long[] t = execute(cs[0], cs[1], cs[2], cs[3], 1000, 1000);
            table.append(String.format("%d, %d, %d, %d, %d, %d, %d, %.3f%n",
                    processors, cs[0], cs[1], cs[3], 1000, t[0], t[1], t[1] * 1.0 / Math.max(1, t[0])));
        }
        System.out.println("\n" + table);
        try (PrintWriter out = new PrintWriter(new File("outputs/speedup-results.txt"), "UTF-8")) { out.print(table); }
    }

    /** Keeps multiplying random-sized matrices every ten seconds. */
    public static void keepGenerating() throws Exception {
        Random random = new Random();
        while (true) {
            execute(1 + random.nextInt(200), 100, 100, 1 + random.nextInt(200), 1 + random.nextInt(5000), 1 + random.nextInt(5000));
            Thread.sleep(10000);
        }
    }

    public static void main(String[] args) throws Exception {
        definedValues();
        if (args.length > 0 && args[0].equals("--forever")) { keepGenerating(); }
    }
}
