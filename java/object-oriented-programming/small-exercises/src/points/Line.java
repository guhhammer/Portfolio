package points;

/** The line y = a*x + b through two points. */
public class Line extends Point {

    public Line(double x, double y) { super(x, y); }

    public float[] through(Point p1, Point p2) {
        float a = ((float) p1.coordinates[1] - (float) p2.coordinates[1]) / ((float) p1.coordinates[0] - (float) p2.coordinates[0]);
        float b = (((float) p1.coordinates[1] + (float) p2.coordinates[1]) - (a * ((float) p1.coordinates[0] + (float) p2.coordinates[0]))) / 2;
        return new float[]{a, b};
    }
}
