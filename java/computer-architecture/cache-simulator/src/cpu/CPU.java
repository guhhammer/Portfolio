package cpu;

import exception.InvalidAddress;
import io.IO;
import memory.AbstractMemory;

/** Reads a [start, end] range from memory at PC and fills it with 1..n through the cache, printing each write. */
public class CPU {

    private final AbstractMemory memory;
    private final IO io;
    private int pc = 0;

    public CPU(AbstractMemory memory, IO io) {
        this.memory = memory;
        this.io = io;
    }

    public void run(int pcAddress) throws InvalidAddress {
        pc = pcAddress;
        int start = memory.get(pc++), end = memory.get(pc);
        for (int i = start; i <= end; ++i) {
            memory.set(i, i - start + 1);
            io.write(i + " -> " + memory.get(i));
        }
    }
}
