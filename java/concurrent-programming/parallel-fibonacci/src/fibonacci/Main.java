package fibonacci;

import java.util.concurrent.Semaphore;

/** Times Fibonacci(45) three ways: naive recursion, recursion with one thread per branch down to the
 *  processor count, and an iterative list. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 45;

        long t = System.currentTimeMillis();
        int sequential = SequentialFibonacci.fib(n);
        System.out.println("Fibonacci(" + n + ") sequential: " + sequential + " | " + (System.currentTimeMillis() - t) + " ms");

        IntBox result = new IntBox();
        Semaphore done = new Semaphore(0);
        ParallelFibonacci parallel = new ParallelFibonacci(n, 0, done, result);
        t = System.currentTimeMillis();
        parallel.start();
        done.acquire();
        System.out.println("Fibonacci(" + n + ") parallel:   " + result.value + " | " + (System.currentTimeMillis() - t) + " ms");

        t = System.currentTimeMillis();
        int iterative = IterativeFibonacci.fib(n);
        System.out.println("Fibonacci(" + n + ") iterative:  " + iterative + " | " + (System.currentTimeMillis() - t) + " ms");
    }
}
