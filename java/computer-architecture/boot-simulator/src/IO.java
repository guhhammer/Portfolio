import java.io.PrintStream;

/** Console output for the boot simulator. */
public class IO {

    private final PrintStream console;
    private final RAM ram;

    public IO(PrintStream console, RAM ram) {
        this.console = console;
        this.ram = ram;
    }

    public void bootBanner(boolean starting) {
        console.print(starting ? "\n\nBooting process:\n\n" : "\nBooting process finished.\n\n");
    }

    public void writeBoot(int address) {
        console.print("Address 0x" + Integer.toHexString(address).toUpperCase()
                + ":  0x" + Integer.toHexString(ram.get(address)).toUpperCase() + "\n");
    }

    public void write(int address) { console.print(ram.get(address)); }
}
