package payroll;

import java.util.concurrent.Semaphore;

/** Barrier exercise: four worker threads each apply one payroll deduction to four slices of an employee list,
 *  meet at a barrier, and then write one report file per slice to outputs/. Concurrent Programming course, PUCPR (2020). */
public class Main {

    public static final int PARTS = 4;
    public static Employee[] employees;

    public static int arrived = 0;
    public static final Semaphore mutex = new Semaphore(1);
    public static final Semaphore barrier = new Semaphore(0);

    public static int start(int slice) { return slice * employees.length / PARTS; }

    public static int end(int slice) { return (slice + 1) * employees.length / PARTS; }

    public static void main(String[] args) {
        int perPart = 20;
        employees = new Employee[perPart * PARTS];
        for (int i = 0; i < employees.length; i++) { employees[i] = new Employee(i); }

        Semaphore[] stepMutexes = { new Semaphore(1), new Semaphore(1), new Semaphore(1), new Semaphore(1) };

        new DeductionWorker(1, Deduction.INCOME_TAX, stepMutexes).start();
        new DeductionWorker(2, Deduction.SOCIAL_SECURITY, stepMutexes).start();
        new DeductionWorker(3, Deduction.PRIVATE_PENSION, stepMutexes).start();
        new DeductionWorker(4, Deduction.HEALTH_PLAN, stepMutexes).start();
    }
}
