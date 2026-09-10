package pipeline;

import java.util.concurrent.Semaphore;

/** A three-stage pipeline of threads passing a baton with semaphores: a Generator makes a random string,
 *  a Filter upper-cases it, an Analyzer counts character occurrences. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static String generatorToFilter = "";    // buffer from the Generator to the Filter
    public static String filterToAnalyzer = "";     // buffer from the Filter to the Analyzer
    public static int sleepTime = 1000;             // idle time of each thread (ms)

    public static void main(String[] args) {
        Semaphore mutexG = new Semaphore(1);   // protects generatorToFilter
        Semaphore mutexA = new Semaphore(1);   // protects filterToAnalyzer

        Semaphore flag1 = new Semaphore(1);    // baton: the Generator goes first
        Semaphore flag2 = new Semaphore(0);    // baton handed to the Filter
        Semaphore flag3 = new Semaphore(1);    // baton between Filter and Analyzer
        Semaphore flag4 = new Semaphore(0);

        new Generator(mutexG, flag1, flag2).start();
        new Filter(mutexG, mutexA, flag2, flag1, flag3, flag4).start();
        new Analyzer(mutexA, flag4, flag3).start();
    }
}
