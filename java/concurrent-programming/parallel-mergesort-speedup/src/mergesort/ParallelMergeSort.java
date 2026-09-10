package mergesort;

import java.util.concurrent.Semaphore;

/** Recursive mergesort that forks one thread per half until 2^level reaches the processor count,
 *  then falls back to the sequential sort; each thread signals its parent through a semaphore. */
public class ParallelMergeSort extends Thread {

    private final int[] arr;
    private final int left, right, level;
    private final Semaphore done;

    public ParallelMergeSort(int[] arr, int left, int right, Semaphore done, int level) {
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.done = done;
        this.level = level;
    }

    private void sort(int[] arr, int l, int r) throws InterruptedException {
        if (l < r) {
            int m = (l + r) / 2;
            if (Math.pow(2, level) >= Runtime.getRuntime().availableProcessors()) {
                SequentialMergeSort.sortRange(arr, l, r);
                done.release();
                return;
            }
            Semaphore halves = new Semaphore(-1);   // turns positive only after both halves release it
            new ParallelMergeSort(arr, l, m, halves, level + 1).start();
            new ParallelMergeSort(arr, m + 1, r, halves, level + 1).start();
            halves.acquire();
            SequentialMergeSort.mergeHalves(arr, l, r);
        }
        done.release();
    }

    @Override
    public void run() {
        try {
            sort(arr, left, right);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
