package bridge;

import java.util.Random;

/** Cars arrive at random from both ends of a one-lane bridge; the Bridge monitor plays the traffic controller.
 *  Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Bridge bridge = new Bridge();
        int cars = args.length > 0 ? Integer.parseInt(args[0]) : 40;

        for (int i = 0; i < cars; i++) {
            Direction d = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
            new Car(i, d, bridge).start();
            Thread.sleep(random.nextInt(150));
        }
    }
}
