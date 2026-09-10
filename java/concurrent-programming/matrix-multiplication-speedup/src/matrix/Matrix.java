package matrix;

import java.io.PrintWriter;
import java.util.Random;

/** A dense integer matrix filled with random values below `bound`. */
public class Matrix {

    private String name = "matrix";
    public final int rows, cols;
    public final int[][] values;
    private int bound = 10000;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.values = new int[rows][cols];
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public void setBound(int bound) { this.bound = bound; }

    public void fillRandom() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) { values[i][j] = Math.abs(random.nextInt(bound)); }
        }
    }

    /** Dot product of row i of a with column j of b. */
    public static int cell(Matrix a, Matrix b, int i, int j) {
        int sum = 0;
        for (int k = 0; k < a.cols; k++) { sum += a.values[i][k] * b.values[k][j]; }
        return sum;
    }

    /** Sequential product a x b, or null when the dimensions do not match. */
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.cols != b.rows) { return null; }
        Matrix c = new Matrix(a.rows, b.cols);
        for (int i = 0; i < c.rows; i++) {
            for (int j = 0; j < c.cols; j++) { c.values[i][j] = cell(a, b, i, j); }
        }
        return c;
    }

    public void write(PrintWriter out) {
        out.println();
        out.println(name);
        out.println();
        for (int i = 0; i < rows; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < cols; j++) { line.append(values[i][j]).append("  "); }
            out.println(line);
        }
    }
}
