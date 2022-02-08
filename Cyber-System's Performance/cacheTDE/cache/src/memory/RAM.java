package memory;

import exception.InvalidAddress;

public class RAM extends MemoryABS{

    private int[] memory = null;
    private int size = 0;

    private void CheckAddress(int address) throws InvalidAddress{
        if (address < 0 || address >= this.size){ throw new InvalidAddress(address); }
    }

    public RAM(int size){
        this.size = size;
        this.memory = new int[size];
    }

    public int Size(){ return this.size; }

    public int Get(int address) throws InvalidAddress{
        CheckAddress(address);
        return this.memory[address];
    }

    public void Set(int address, int word) throws InvalidAddress{
        CheckAddress(address);
        this.memory[address] = word;
    }

}
