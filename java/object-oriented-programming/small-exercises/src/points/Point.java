package points;

public class Point {

    protected final double[] coordinates = new double[2];

    public Point(double x, double y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    @Override
    public String toString() { return "Point{x=" + coordinates[0] + ", y=" + coordinates[1] + "}"; }
}
