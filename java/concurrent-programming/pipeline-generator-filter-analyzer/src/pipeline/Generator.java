package pipeline;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {

    private final Semaphore mutex, myTurn, nextTurn;

    public Generator(Semaphore mutex, Semaphore myTurn, Semaphore nextTurn) {
        this.mutex = mutex;
        this.myTurn = myTurn;
        this.nextTurn = nextTurn;
    }

    public static String randomString(Random random, int length) {
        StringBuilder sb = new StringBuilder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                myTurn.acquire();
                mutex.acquire();
                Main.generatorToFilter = randomString(random, 10);
                System.out.println("\n\nGenerator: \t" + Main.generatorToFilter);
                mutex.release();
                nextTurn.release();
                Thread.sleep(Main.sleepTime);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
