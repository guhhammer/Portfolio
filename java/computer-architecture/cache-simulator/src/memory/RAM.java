package memory;

import exception.InvalidAddress;

public class RAM extends AbstractMemory {

    private final int[] memory;
    private final int size;

    public RAM(int size) {
        this.size = size;
        this.memory = new int[size];
    }

    private void checkAddress(int address) throws InvalidAddress {
        if (address < 0 || address >= size) { throw new InvalidAddress(address); }
    }

    public int size() { return size; }

    public int get(int address) throws InvalidAddress {
        checkAddress(address);
        return memory[address];
    }

    public void set(int address, int word) throws InvalidAddress {
        checkAddress(address);
        memory[address] = word;
    }
}
