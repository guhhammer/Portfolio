package memory;

import exception.InvalidAddress;

public abstract class MemoryABS {

    public abstract int Size();

    public abstract int Get(int address) throws InvalidAddress;

    public abstract void Set(int address, int word) throws InvalidAddress;

}
