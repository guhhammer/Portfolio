package syncmonitor;

/** Bounded buffer as a Java monitor with synchronized methods and wait/notify (no explicit conditions). */
public class BoundedBuffer {

    private final int capacity;
    private final int[] buffer;
    private int front = 0, rear = 0, count = 0;

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new int[capacity];
    }

    public synchronized void put(int item) throws InterruptedException {
        while (count == capacity) { wait(); }
        buffer[rear] = item;
        rear = (rear + 1) % capacity;
        count++;
        System.out.println("Produced: " + item + " [" + count + "]");
        notify();   // signal
    }

    public synchronized int take() throws InterruptedException {
        while (count == 0) { wait(); }
        int item = buffer[front];
        front = (front + 1) % capacity;
        count--;
        System.out.println("Consumed: " + item + " [" + count + "]");
        notify();   // signal
        return item;
    }
}
