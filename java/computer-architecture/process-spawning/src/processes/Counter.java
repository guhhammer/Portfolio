package processes;

/** Child process: prints its PID and a counter N times, sleeping T seconds between prints.
 *  Usage: java processes.Counter <N> <T> */
public class Counter {

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.err.println("Usage: Counter <N> <T>");
            System.exit(1);
        }

        long pid = ProcessHandle.current().pid();
        int n = Integer.parseInt(args[0]);
        float t = Float.parseFloat(args[1]);

        for (int i = 1; i <= n; ++i) {
            System.out.println(pid + "> " + i);
            Thread.sleep((long) (t * 1000));
        }
    }
}
