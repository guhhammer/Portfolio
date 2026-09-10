package monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Bounded buffer as a monitor with explicit conditions (ReentrantLock + notFull / notEmpty). */
public class BoundedBuffer {

    private final int capacity;
    private final int[] buffer;
    private int front = 0, rear = 0, count = 0;

    private final Lock mutex = new ReentrantLock();
    private final Condition notFull = mutex.newCondition();
    private final Condition notEmpty = mutex.newCondition();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new int[capacity];
    }

    public void put(int item) throws InterruptedException {
        mutex.lock();
        try {
            while (count == capacity) { notFull.await(); }
            buffer[rear] = item;
            rear = (rear + 1) % capacity;
            count++;
            System.out.println("Produced: " + item + " [" + count + "]");
            notEmpty.signal();
        } finally {
            mutex.unlock();
        }
    }

    public int take() throws InterruptedException {
        mutex.lock();
        try {
            while (count == 0) { notEmpty.await(); }
            int item = buffer[front];
            front = (front + 1) % capacity;
            count--;
            System.out.println("Consumed: " + item + " [" + count + "]");
            notFull.signal();
            return item;
        } finally {
            mutex.unlock();
        }
    }
}
