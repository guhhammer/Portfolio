package payroll;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Semaphore;

/** Applies one deduction to the four slices of the employee list, in rotated order so that the four workers
 *  touch different slices at the same time; then waits at the barrier and writes the report of its own slice. */
public class DeductionWorker extends Thread {

    private final int id;                 // 1..4: the slice this worker starts with and reports on
    private final Deduction deduction;
    private final Semaphore[] stepMutexes;

    public DeductionWorker(int id, Deduction deduction, Semaphore[] stepMutexes) {
        this.id = id;
        this.deduction = deduction;
        this.stepMutexes = stepMutexes;
    }

    @Override
    public void run() {
        try {
            for (int step = 0; step < Main.PARTS; step++) {
                int slice = (id - 1 + step) % Main.PARTS;
                stepMutexes[step].acquire();
                for (int i = Main.start(slice); i < Main.end(slice); i++) {
                    deduction.apply(Main.employees[i]);
                }
                System.out.println("\n" + deduction.label + " (part " + (slice + 1) + "): done.\n");
                stepMutexes[step].release();
            }

            // barrier (turnstile pattern): the last worker to arrive lets everyone through.
            Main.mutex.acquire();
            Main.arrived += 1;
            if (Main.arrived == Main.PARTS) { Main.barrier.release(); }
            Main.mutex.release();
            Main.barrier.acquire();
            Main.barrier.release();

            // every deduction has been applied: report this worker's slice.
            StringBuilder report = new StringBuilder();
            for (int i = Main.start(id - 1); i < Main.end(id - 1); i++) {
                report.append(Main.employees[i].report());
            }
            new File("outputs").mkdirs();
            try (FileWriter file = new FileWriter("outputs/part-" + id + ".txt")) {
                file.write(report.toString());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
