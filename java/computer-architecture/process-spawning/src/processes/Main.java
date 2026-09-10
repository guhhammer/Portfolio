package processes;

/** Parent process: spawns two Counter processes with different counts and periods and waits for them.
 *
 *  Q1. Two counters with different periods and counts: do they run in parallel?
 *      Yes. The processes run concurrently, and with waitFor() the parent only exits after both finish.
 *  Q2. If the parent finishes first, does the child keep running?
 *      Yes. Set WAIT_FOR_CHILDREN to false to watch the children outlive the parent.
 */
public class Main {

    private static final boolean WAIT_FOR_CHILDREN = true;

    public static void main(String[] args) {
        try {
            // equivalent command line: java processes.Counter 10 0.5
            ProcessBuilder first = new ProcessBuilder("java", "processes.Counter", "10", "0.5");
            ProcessBuilder second = new ProcessBuilder("java", "processes.Counter", "20", "0.7");
            first.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            second.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process a = first.start();
            Process b = second.start();

            if (WAIT_FOR_CHILDREN) {
                a.waitFor();
                b.waitFor();
            }
            System.out.println("Parent finishing.");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
