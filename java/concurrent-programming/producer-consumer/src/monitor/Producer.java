package monitor;

import java.util.Random;

public class Producer extends Thread {

    private final BoundedBuffer buffer;
    private final Random random = new Random();

    public Producer(BoundedBuffer buffer) { this.buffer = buffer; }

    @Override
    public void run() {
        while (true) {
            try {
                buffer.put(random.nextInt(1000));
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
