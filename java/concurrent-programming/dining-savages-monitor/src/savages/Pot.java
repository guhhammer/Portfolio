package savages;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** The pot as a monitor with three gates: A for the cook, B for savages waiting on a refill,
 *  C for the savage that asked for the refill (scoreboard pattern). */
public class Pot {

    private final int capacity;
    private int availableFood = 0;
    private int orangeSavages = 0;
    private boolean thereIsARedSavage = false;

    private final Lock gateM = new ReentrantLock();
    private final Condition gateA = gateM.newCondition();
    private final Condition gateB = gateM.newCondition();
    private final Condition gateC = gateM.newCondition();

    public Pot(int capacity) { this.capacity = capacity; }

    public void fill() {
        try {
            gateM.lock();
            System.out.println("*** Cook waits in his lounge");
            gateA.await();
            System.out.println("*** Cook enters the monitor");
            try {
                Thread.sleep(1000 + new Random().nextInt(1000));   // cooking time
            } catch (InterruptedException e) { e.printStackTrace(); }
            availableFood = capacity;
            gateC.signal();
            System.out.println("*** Cook fills the pot and signals gate C");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            gateM.unlock();
        }
    }

    public void getServed(Savage savage) {
        try {
            gateM.lock();
            boolean served = false;
            while (!served) {
                System.out.print("Savage enters the pot monitor: " + savage.getSavageId() + ", " + savage.getColor() + " ==> ");
                switch (savage.getColor()) {
                    case GREEN:
                        System.out.println("shouldn't be here!");
                        break;
                    case YELLOW:
                        if (thereIsARedSavage || orangeSavages > 0) {
                            savage.setColor(Color.ORANGE);
                            orangeSavages++;
                            System.out.println("becomes orange and waits at gate B");
                            gateB.await();
                        } else if (availableFood == 0) {
                            savage.setColor(Color.RED);
                            thereIsARedSavage = true;
                            gateA.signal();
                            System.out.println("becomes red, signals gate A and waits at gate C");
                            gateC.await();
                        } else {
                            availableFood--;
                            served = true;
                            savage.setColor(Color.GREEN);
                            // gateB.signal() is not needed here, but would not hurt either
                            System.out.println("gets served, becomes green and signals gate B");
                        }
                        break;
                    case ORANGE:
                        if (availableFood == 0) {
                            orangeSavages--;
                            savage.setColor(Color.RED);
                            thereIsARedSavage = true;
                            gateA.signal();
                            System.out.println("becomes red, signals gate A and waits at gate C");
                            gateC.await();
                        } else {
                            availableFood--;
                            served = true;
                            orangeSavages--;
                            savage.setColor(Color.GREEN);
                            gateB.signal();
                            System.out.println("gets served, becomes green and signals gate B");
                        }
                        break;
                    case RED:
                        availableFood--;
                        served = true;
                        thereIsARedSavage = false;
                        savage.setColor(Color.GREEN);
                        gateB.signal();
                        System.out.println("gets served, becomes green and signals gate B");
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            gateM.unlock();
        }
    }
}
