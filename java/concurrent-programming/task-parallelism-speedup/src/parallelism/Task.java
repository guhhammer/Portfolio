package parallelism;

import java.util.concurrent.Semaphore;

/** One thread of the parallel run: performs its share of the calculation and signals completion. */
public class Task extends Thread {

    private final int id;
    private final long sequenceSize;
    private final Semaphore done;

    public Task(int id, long sequenceSize, Semaphore done) {
        this.id = id;
        this.sequenceSize = sequenceSize;
        this.done = done;
    }

    public int getTaskId() { return id; }

    @Override
    public void run() {
        new Calculation(sequenceSize).execute();
        done.release();
    }
}
