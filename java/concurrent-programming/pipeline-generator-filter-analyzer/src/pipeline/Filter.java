package pipeline;

import java.util.concurrent.Semaphore;

public class Filter extends Thread {

    private final Semaphore mutexG, mutexA, myTurnG, nextTurnG, myTurnA, nextTurnA;

    public Filter(Semaphore mutexG, Semaphore mutexA, Semaphore myTurnG, Semaphore nextTurnG,
                  Semaphore myTurnA, Semaphore nextTurnA) {
        this.mutexG = mutexG;
        this.mutexA = mutexA;
        this.myTurnG = myTurnG;
        this.nextTurnG = nextTurnG;
        this.myTurnA = myTurnA;
        this.nextTurnA = nextTurnA;
    }

    @Override
    public void run() {
        while (true) {
            try {
                myTurnG.acquire();
                myTurnA.acquire();
                mutexG.acquire();
                mutexA.acquire();
                Main.filterToAnalyzer = Main.generatorToFilter.toUpperCase();   // read, then write
                System.out.println("Filter: \t" + Main.filterToAnalyzer);
                mutexG.release();
                mutexA.release();
                nextTurnA.release();
                nextTurnG.release();
                Thread.sleep(Main.sleepTime);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
