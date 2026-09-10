package merge;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Worker extends Thread {

    private static final Random generator = new Random();
    public static int fileCounter = 1;

    private final int id;
    private final String fileName;
    private final Semaphore mutex, queueMutex, signal, entryBarrier, exitBarrier;

    public Worker(int id, String fileName, Semaphore mutex, Semaphore queueMutex, Semaphore signal,
                  Semaphore entryBarrier, Semaphore exitBarrier) {
        this.id = id;
        this.fileName = fileName;
        this.mutex = mutex;
        this.queueMutex = queueMutex;
        this.signal = signal;
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int[] array = new int[Main.NUMBER_COUNT];
                for (int i = 0; i < array.length; i++) { array[i] = generator.nextInt(Main.LIMIT); }
                Arrays.sort(array);

                String path = "outputs/" + fileName + "_" + fileCounter + ".txt";
                try (FileWriter file = new FileWriter(path)) {
                    for (int value : array) { file.write(value + "\n"); }
                }
                System.out.println("File " + fileName + "_" + fileCounter + " was saved.\n");

                // first turnstile: wait for all workers to arrive.
                mutex.acquire();
                Main.counter += 1;
                if (Main.counter == Main.WORKERS) {
                    exitBarrier.acquire();
                    entryBarrier.release();
                }
                mutex.release();
                entryBarrier.acquire();
                entryBarrier.release();

                queueMutex.acquire();
                Main.fileQueue.offer(path);
                queueMutex.release();

                Thread.sleep(Main.WAIT_MS);
                System.out.println("File " + fileName + "_" + fileCounter + " was added to the queue.\n");

                signal.release();   // tell the combiner this worker's file is ready

                // second turnstile: wait for all workers to leave, so the barrier can be reused.
                mutex.acquire();
                Main.counter -= 1;
                if (Main.counter == 0) {
                    entryBarrier.acquire();
                    exitBarrier.release();
                }
                mutex.release();
                exitBarrier.acquire();
                exitBarrier.release();

                Thread.sleep(Main.WAIT_MS);
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
