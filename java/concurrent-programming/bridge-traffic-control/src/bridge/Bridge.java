package bridge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** A one-lane bridge shared by cars coming from both ends. Cars cross only in the current direction, at most
 *  CAPACITY at a time; after BATCH cars have crossed in one direction while cars wait on the other side, the
 *  controller drains the bridge and switches direction so neither side starves. */
public class Bridge {

    public static final int CAPACITY = 5;
    public static final int BATCH = 10;

    private final Lock lock = new ReentrantLock();
    private final Condition mayEnter = lock.newCondition();

    private Direction current = Direction.SOUTH;
    private int onBridge = 0;
    private int crossedThisTurn = 0;
    private final int[] waiting = new int[2];

    private boolean mustYield(Direction d) {
        return crossedThisTurn >= BATCH && waiting[d.opposite().ordinal()] > 0;
    }

    public void enter(Direction d, int id) throws InterruptedException {
        lock.lock();
        try {
            waiting[d.ordinal()]++;
            while (!(onBridge == 0 && (current == d || !mustYield(d.opposite()) || waiting[d.opposite().ordinal()] == 0)
                     || (current == d && onBridge > 0 && onBridge < CAPACITY && !mustYield(d)))) {
                mayEnter.await();
            }
            waiting[d.ordinal()]--;
            if (current != d) {
                current = d;
                crossedThisTurn = 0;
                System.out.println("== controller: direction is now " + d);
            }
            onBridge++;
            crossedThisTurn++;
            System.out.printf("car %d (%s) entered the bridge  [on bridge: %d, this turn: %d]%n", id, d, onBridge, crossedThisTurn);
        } finally {
            lock.unlock();
        }
    }

    public void leave(Direction d, int id) {
        lock.lock();
        try {
            onBridge--;
            System.out.printf("car %d (%s) left the bridge     [on bridge: %d]%n", id, d, onBridge);
            if (onBridge == 0 && mustYield(d)) {
                current = d.opposite();       // hand the bridge to the other side
                crossedThisTurn = 0;
                System.out.println("== controller: direction is now " + current);
            }
            mayEnter.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
