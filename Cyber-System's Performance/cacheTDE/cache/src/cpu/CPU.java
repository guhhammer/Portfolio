package cpu;

import exception.InvalidAddress;
import memory.MemoryABS;
import io.IO;

public class CPU {

    private MemoryABS ram = null;
    private IO io = null;
    private int PC = 0;

    public CPU(MemoryABS r, IO es){
        this.ram = r;
        this.io = es;
    }

    public void Run(int pc_address) throws InvalidAddress {

        PC = pc_address;

        int iaddr = ram.Get(PC++), faddr = ram.Get(PC);

        for(int i=iaddr; i <=faddr; ++i){
            ram.Set(i, i - iaddr + 1);
            io.Write(i + " -> " + ram.Get(i));
        }

    }

}
