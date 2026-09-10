package hilzer;

import java.util.concurrent.Semaphore;

/** A fixed-size FIFO of customer tickets (one semaphore per waiting customer). */
public class SemaphoreQueue {

    private final Semaphore[] data;
    private int first = 0, last = 0, size = 0;
    private final int capacity;

    public SemaphoreQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Semaphore[capacity];
    }

    public int capacity() { return capacity; }

    protected boolean isEmpty() { return size == 0; }

    protected boolean isFull() { return size == capacity - 1; }

    protected void insert(Semaphore ticket) {
        if (!isFull()) {
            data[last++] = ticket;
            size++;
            if (last == capacity - 1) { last = 0; }
        }
    }

    protected Semaphore remove() {
        Semaphore ticket = null;
        if (!isEmpty()) {
            ticket = data[first];
            data[first++] = null;
            size--;
            if (first == capacity - 1) { first = 0; }
        }
        return ticket;
    }
}
