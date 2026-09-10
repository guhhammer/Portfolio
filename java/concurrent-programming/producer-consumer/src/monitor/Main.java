package monitor;

/** Producer-consumer on a monitor with explicit conditions. */
public class Main {

    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(10);
        new Producer(buffer).start();
        new Consumer(buffer).start();
    }
}
