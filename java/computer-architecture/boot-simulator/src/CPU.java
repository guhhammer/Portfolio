/** A minimal CPU that boots by walking the address range stored in the first two words of RAM. */
public class CPU {

    private final RAM ram;
    private final IO io;
    private int pc;

    public CPU(RAM ram, IO io) {
        this.ram = ram;
        this.io = io;
        this.pc = 0;
    }

    /** RAM[0] and RAM[1] hold the start and end of the boot area; each word in it is "executed" and displayed. */
    public void boot() {
        int lower = ram.get(pc), upper = ram.get(pc + 1);

        io.bootBanner(true);
        while (lower <= upper) {
            int instruction = ram.get(lower);   // execute instruction...
            io.writeBoot(lower++);              // display the value.
        }
        io.bootBanner(false);
    }

    public void run() {
        boot();
        // do something...
    }
}
