import java.io.PrintStream;

public class IO {
    private final PrintStream console;
    public IO(PrintStream console) { this.console = console; }
    public void write(String s) { console.println(s); }
}
