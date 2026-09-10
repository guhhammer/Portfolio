package fibonacci;

import java.util.concurrent.Semaphore;

/** Recursive Fibonacci that forks a thread per branch until 2^level reaches the processor count,
 *  then continues sequentially; each thread signals its parent through a semaphore. */
public class ParallelFibonacci extends Thread {

    private final int n, level;
    private final IntBox result;
    private final Semaphore done;

    public ParallelFibonacci(int n, int level, Semaphore done, IntBox result) {
        this.n = n;
        this.level = level;
        this.done = done;
        this.result = result;
    }

    private void compute() {
        if (n == 0) { result.value = 0; return; }
        if (n == 1) { result.value = 1; return; }

        if (Math.pow(2, level) >= Runtime.getRuntime().availableProcessors()) {
            result.value = SequentialFibonacci.fib(n);
            return;
        }

        Semaphore children = new Semaphore(-1);   // turns positive after both children release it
        IntBox a = new IntBox(), b = new IntBox();
        new ParallelFibonacci(n - 1, level + 1, children, a).start();
        new ParallelFibonacci(n - 2, level + 1, children, b).start();
        try { children.acquire(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        result.value = a.value + b.value;
    }

    @Override
    public void run() {
        compute();
        done.release();
    }
}
