package mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/** Benchmarks sequential vs parallel mergesort on arrays of 2^15 .. 2^26 random ints (50 runs each), prints
 *  N, sequential ms, parallel ms and speed-up, then writes a Python script that charts the results in a spreadsheet.
 *  Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static final int LEFT_LIMIT = 15, RIGHT_LIMIT = 26, REPEATS = 50;

    /** `multiplier` widens the range of Random.nextInt. */
    public static int[] randomArray(int n, int multiplier) {
        int[] out = new int[n];
        Random random = new Random();
        for (int i = 0; i < out.length; i++) { out[i] = random.nextInt(n * multiplier); }
        return out;
    }

    public static List<double[]> benchmark(int leftLimit, int rightLimit) throws InterruptedException {
        List<double[]> rows = new ArrayList<>();
        System.out.println("N, sequential ms, parallel ms, speed-up:\n");
        for (int i = leftLimit; i <= rightLimit; i++) {
            int[] x = randomArray((int) Math.pow(2, i), 10);
            long sequentialTotal = 0, parallelTotal = 0;
            for (int j = 0; j < REPEATS; j++) {
                long t = System.currentTimeMillis();
                SequentialMergeSort.mergeSort(x.clone());
                sequentialTotal += System.currentTimeMillis() - t;

                Semaphore done = new Semaphore(0);
                ParallelMergeSort sorter = new ParallelMergeSort(x.clone(), 0, x.length - 1, done, 0);
                t = System.currentTimeMillis();
                sorter.start();
                done.acquire();
                parallelTotal += System.currentTimeMillis() - t;
            }
            double sequential = sequentialTotal / (double) REPEATS, parallel = parallelTotal / (double) REPEATS;
            rows.add(new double[]{i, sequential, parallel});
            System.out.println(i + ", " + sequential + ", " + parallel + ", " + sequential / Math.max(parallel, 1e-9) + "\n");
        }
        return rows;
    }

    public static void main(String[] args) throws Exception {
        List<double[]> rows = benchmark(LEFT_LIMIT, RIGHT_LIMIT);
        ChartScriptWriter.write(rows, LEFT_LIMIT, RIGHT_LIMIT);
        ChartScriptWriter.runAndOpen();
    }
}
