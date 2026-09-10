package syncmonitor;

/** Producer-consumer on a synchronized/wait/notify monitor. */
public class Main {

    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(10);
        new Producer(buffer).start();
        new Consumer(buffer).start();
    }
}
