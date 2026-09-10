package memory;

import exception.InvalidAddress;

/** One cache line: `size` words, a tag, and a dirty flag that is set on every write. */
public class CacheLine {

    private final int[] memory;
    private final int size;
    private int tag;
    private boolean dirty = false;

    public CacheLine(int size, int tag) {
        this.size = size;
        this.memory = new int[size];
        this.tag = tag;
    }

    private void checkAddress(int address) throws InvalidAddress {
        if (address < 0 || address >= size) { throw new InvalidAddress(address); }
    }

    public boolean isDirty() { return dirty; }

    public void setTag(int tag) { this.tag = tag; }

    public int getTag() { return tag; }

    public int size() { return size; }

    public int get(int address) throws InvalidAddress {
        checkAddress(address);
        return memory[address];
    }

    public void set(int address, int word) throws InvalidAddress {
        checkAddress(address);
        memory[address] = word;
        dirty = true;
    }
}
