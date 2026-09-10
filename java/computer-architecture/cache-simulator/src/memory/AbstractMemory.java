package memory;

import exception.InvalidAddress;

public abstract class AbstractMemory {
    public abstract int size();
    public abstract int get(int address) throws InvalidAddress;
    public abstract void set(int address, int word) throws InvalidAddress;
}
