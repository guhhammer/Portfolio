package pipeline;

import java.util.concurrent.Semaphore;

public class Analyzer extends Thread {

    private final Semaphore mutex, myTurn, nextTurn;
    private static final int ASCII = 256;

    public Analyzer(Semaphore mutex, Semaphore myTurn, Semaphore nextTurn) {
        this.mutex = mutex;
        this.myTurn = myTurn;
        this.nextTurn = nextTurn;
    }

    /** Prints how many times each distinct character occurs in the string. */
    static void occurrences(String text) {
        int[] count = new int[ASCII];
        int len = text.length();
        for (int i = 0; i < len; i++) { count[text.charAt(i)]++; }

        char[] seen = new char[len];
        for (int i = 0; i < len; i++) {
            seen[i] = text.charAt(i);
            int found = 0;
            for (int j = 0; j <= i; j++) { if (text.charAt(i) == seen[j]) { found++; } }
            if (found == 1) {
                System.out.println("\tOccurrences of (" + text.charAt(i) + "):   " + count[text.charAt(i)]);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                myTurn.acquire();
                mutex.acquire();
                System.out.println("Occurrences: ");
                occurrences(Main.filterToAnalyzer);
                mutex.release();
                nextTurn.release();
                Thread.sleep(Main.sleepTime);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
