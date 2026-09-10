package matrix;

import java.util.Queue;
import java.util.concurrent.Semaphore;

/** Computes the cells of C assigned to this thread, then signals completion. */
public class Multiplier extends Thread {

    private final Matrix a, b, c;
    private final Queue<int[]> cells;
    private final Semaphore done;
    public long elapsedMs = 0;

    public Multiplier(Matrix a, Matrix b, Matrix c, Queue<int[]> cells, Semaphore done) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.cells = cells;
        this.done = done;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        if (a.cols == b.rows) {
            while (!cells.isEmpty()) {
                int[] cell = cells.poll();
                c.values[cell[0]][cell[1]] = Matrix.cell(a, b, cell[0], cell[1]);
            }
        }
        elapsedMs = System.currentTimeMillis() - start;
        done.release();
    }
}
