package syncmonitor;

import java.util.Random;

public class Consumer extends Thread {

    private final BoundedBuffer buffer;
    private final Random random = new Random();

    public Consumer(BoundedBuffer buffer) { this.buffer = buffer; }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.take();
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
