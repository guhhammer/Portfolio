package parallelism;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

/** Performance-evaluation study: splits a CPU-bound calculation of 2^N steps across tasks and measures
 *  (A) time vs N, (B) time vs number of processors, (C) speed-up and (D) efficiency, for one and two tasks per
 *  processor; then charts everything in a spreadsheet. Concurrent Programming course, PUCPR (2020).
 *  Usage: java parallelism.Launcher [repeats] [minExponent] [maxExponent] (defaults 50, 15, 28). */
public class Launcher {

    public static long executeSequential(long sequenceSize) {
        long start = System.currentTimeMillis();
        new Calculation(sequenceSize).execute();
        return System.currentTimeMillis() - start;
    }

    public static long executeParallel(long sequenceSize, int taskCount) throws InterruptedException {
        long perTask = sequenceSize / taskCount;
        Semaphore done = new Semaphore(1 - taskCount);   // turns positive when the last task releases it
        Task[] tasks = new Task[taskCount];
        for (int i = 0; i < taskCount; i++) { tasks[i] = new Task(i, perTask, done); }

        long start = System.currentTimeMillis();
        for (Task task : tasks) { task.start(); }
        done.acquire();
        return System.currentTimeMillis() - start;
    }

    private static int processors() { return Runtime.getRuntime().availableProcessors(); }

    private static void banner(String chart, int tasksPerProcessor) {
        System.out.printf("%n%nChart %s: %d processors, %d tasks (%d per processor)%n%n",
                chart, processors(), tasksPerProcessor * processors(), tasksPerProcessor);
    }

    /** (A) parallel time as N grows, with tasksPerProcessor * processors tasks. */
    public static List<double[]> chartA(int repeats, int min, int max, int tasksPerProcessor) throws InterruptedException {
        banner("A", tasksPerProcessor);
        int taskCount = tasksPerProcessor * processors();
        List<double[]> rows = new ArrayList<>();
        System.out.println("N, sequence size, parallel time");
        for (int n = min; n <= max; n++) {
            long size = (long) Math.pow(2, n);
            double parallel = 0;
            for (int i = 0; i < repeats; i++) { parallel += executeParallel(size, taskCount); }
            rows.add(new double[]{n, parallel / repeats});
            System.out.println(n + ", " + size + ", " + parallel / repeats);
        }
        return rows;
    }

    /** (B) parallel time at N = max as the number of processors used grows. */
    public static List<double[]> chartB(int repeats, int max, int tasksPerProcessor) throws InterruptedException {
        banner("B", tasksPerProcessor);
        List<double[]> rows = new ArrayList<>();
        long size = (long) Math.pow(2, max);
        System.out.println("N, processors, parallel time");
        for (int p = 1; p <= processors(); p++) {
            double parallel = 0;
            for (int i = 0; i < repeats; i++) { parallel += executeParallel(size, tasksPerProcessor * p); }
            rows.add(new double[]{max, p, parallel / repeats});
            System.out.println(max + ", " + p + ", " + parallel / repeats);
        }
        return rows;
    }

    /** (C) speed-up = sequential time / parallel time at N = max, per number of processors. */
    public static List<double[]> chartC(int repeats, int max, int tasksPerProcessor) throws InterruptedException {
        banner("C", tasksPerProcessor);
        List<double[]> rows = new ArrayList<>();
        rows.add(new double[]{max, 1, 1});
        long size = (long) Math.pow(2, max);
        System.out.println("N, processors, speed-up\n" + max + ", 1, 1");
        for (int p = 2; p <= processors(); p++) {
            double sequential = 0, parallel = 0;
            for (int i = 0; i < repeats; i++) {
                sequential += executeSequential(size);
                parallel += executeParallel(size, tasksPerProcessor * p);
            }
            double speedUp = sequential / Math.max(parallel, 1e-9);
            rows.add(new double[]{max, p, speedUp});
            System.out.println(max + ", " + p + ", " + speedUp);
        }
        return rows;
    }

    /** (D) efficiency = speed-up / processors at N = max. */
    public static List<double[]> chartD(int repeats, int max, int tasksPerProcessor) throws InterruptedException {
        banner("D", tasksPerProcessor);
        List<double[]> rows = new ArrayList<>();
        rows.add(new double[]{max, 1, 1});
        long size = (long) Math.pow(2, max);
        System.out.println("N, processors, efficiency\n" + max + ", 1, 1");
        for (int p = 2; p <= processors(); p++) {
            double sequential = 0, parallel = 0;
            for (int i = 0; i < repeats; i++) {
                sequential += executeSequential(size);
                parallel += executeParallel(size, tasksPerProcessor * p);
            }
            double efficiency = (sequential / Math.max(parallel, 1e-9)) / p;
            rows.add(new double[]{max, p, efficiency});
            System.out.println(max + ", " + p + ", " + efficiency);
        }
        return rows;
    }

    public static void main(String[] args) throws Exception {
        int repeats = args.length > 0 ? Integer.parseInt(args[0]) : 50;
        int min = args.length > 1 ? Integer.parseInt(args[1]) : 15;
        int max = args.length > 2 ? Integer.parseInt(args[2]) : 28;

        List<ChartScriptWriter.Dataset> datasets = new ArrayList<>();
        for (int tasksPerProcessor = 1; tasksPerProcessor <= 2; tasksPerProcessor++) {
            String suffix = String.valueOf(tasksPerProcessor);
            datasets.add(new ChartScriptWriter.Dataset("A" + suffix, new String[]{"N (2^size)", "Time"}, chartA(repeats, min, max, tasksPerProcessor)));
            datasets.add(new ChartScriptWriter.Dataset("B" + suffix, new String[]{"N (2^max size)", "Processors", "Time"}, chartB(repeats, max, tasksPerProcessor)));
            datasets.add(new ChartScriptWriter.Dataset("C" + suffix, new String[]{"N (2^max size)", "Processors", "Speed-up"}, chartC(repeats, max, tasksPerProcessor)));
            datasets.add(new ChartScriptWriter.Dataset("D" + suffix, new String[]{"N (2^max size)", "Processors", "Efficiency"}, chartD(repeats, max, tasksPerProcessor)));
        }
        System.out.println("\nDatasets: " + Arrays.toString(datasets.stream().map(d -> d.name).toArray()));
        ChartScriptWriter.write(datasets);
        ChartScriptWriter.runAndOpen();
    }
}
