package merge;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/** Waits for the four worker files of a round, merges them in order and drops duplicates. */
public class Combiner extends Thread {

    private final Semaphore worker1, worker2, worker3, worker4;
    private int mergeFileCounter = 1;

    public Combiner(Semaphore worker1, Semaphore worker2, Semaphore worker3, Semaphore worker4) {
        this.worker1 = worker1;
        this.worker2 = worker2;
        this.worker3 = worker3;
        this.worker4 = worker4;
    }

    private static int[] readFile(String path) throws Exception {
        int[] values = new int[Main.NUMBER_COUNT];
        int count = 0;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextInt()) { values[count++] = scanner.nextInt(); }
        }
        return values;
    }

    @Override
    public void run() {
        while (true) {
            try {
                worker1.acquire();
                worker2.acquire();     // wait for all four workers
                worker3.acquire();
                worker4.acquire();

                Worker.fileCounter += 1;
                Thread.sleep(Main.WAIT_MS);

                int[][] arrays = new int[Main.WORKERS][];
                for (int w = 0; w < Main.WORKERS; w++) {
                    String path = Main.fileQueue.poll();   // read and remove the head of the queue
                    System.out.println("Combiner removed file Thread" + (w + 1) + "_" + (Worker.fileCounter - 1) + ".txt from the queue.\n");
                    arrays[w] = readFile(path);
                }

                int[] merged = merge(merge(arrays[0], arrays[1]), merge(arrays[2], arrays[3]));
                int[] distinct = IntStream.of(merged).distinct().toArray();
                System.out.println("Combiner merged the files and removed the duplicates.\n");

                String path = "outputs/MergeThreads_" + mergeFileCounter + ".txt";
                try (FileWriter file = new FileWriter(path)) {
                    for (int value : distinct) { file.write(value + "\n"); }
                }
                System.out.println("File MergeThreads_" + mergeFileCounter + ".txt was saved.\n");
                mergeFileCounter += 1;
                Thread.sleep(Main.WAIT_MS);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public int[] merge(int[] a, int[] b) {
        int[] out = new int[a.length + b.length];
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) { out[i + j] = a[i++]; } else { out[i + j] = b[j++]; }
        }
        while (i < a.length) { out[i + j] = a[i++]; }
        while (j < b.length) { out[i + j] = b[j++]; }
        return out;
    }
}
