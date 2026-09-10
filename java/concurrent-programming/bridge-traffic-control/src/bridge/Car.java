package bridge;

import java.util.Random;

public class Car extends Thread {

    private static final Random random = new Random();
    private final int id;
    private final Direction direction;
    private final Bridge bridge;

    public Car(int id, Direction direction, Bridge bridge) {
        this.id = id;
        this.direction = direction;
        this.bridge = bridge;
    }

    @Override
    public void run() {
        try {
            System.out.printf("car %d arrived from the %s%n", id, direction);
            bridge.enter(direction, id);
            Thread.sleep(300 + random.nextInt(500));   // crossing time
            bridge.leave(direction, id);
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
