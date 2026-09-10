package points;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(8, 5);
        System.out.println(p1 + " " + p2);
        System.out.println("line through them (a, b): " + Arrays.toString(new Line(3, 1).through(p1, p2)));
    }
}
