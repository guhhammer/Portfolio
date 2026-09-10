/** Reads a [start, end] range from memory at PC and fills it with 1..n, printing each write. */
public class CPU {

    private final Memory memory;
    private final IO io;
    private int pc = 0;

    public CPU(Memory memory, IO io) {
        this.memory = memory;
        this.io = io;
    }

    public void run(int pcAddress) {
        pc = pcAddress;
        int start = memory.get(pc++);
        int end = memory.get(pc);
        for (int i = start; i <= end; ++i) {
            memory.set(i, i - start + 1);
            io.write(i + " -> " + memory.get(i));
        }
    }
}
