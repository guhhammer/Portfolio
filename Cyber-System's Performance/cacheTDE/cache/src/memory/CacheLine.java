package memory;

import exception.InvalidAddress;

public class CacheLine {
    private int memory[] = null, size = 0, tag;
    private boolean flag = false;

    private void CheckAddress(int address) throws InvalidAddress {
        if (address < 0 || address >= this.size){ throw new InvalidAddress(address); }
    }

    public CacheLine(int size, int tag) {
        this.size = size;
        this.memory = new int[size];
        this.tag = tag;
    }

    public boolean getFlag(){ return this.flag; }

    public void setTag(int tag){ this.tag = tag; }

    public int getTag(){ return this.tag; }

    public int Size() { return this.size; }

    public int Get(int address) throws InvalidAddress {
        CheckAddress(address);
        return this.memory[address];
    }

    public void Set(int address, int word) throws InvalidAddress {
        CheckAddress(address);
        this.memory[address] = word;
        this.flag = true;
    }

}
