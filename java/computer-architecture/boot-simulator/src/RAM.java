/** Word-addressed main memory. */
public class RAM {

    private final int[] memory;

    public RAM(int size) { this.memory = new int[size]; }

    public int get(int address) { return memory[address]; }

    public void set(int address, int word) { memory[address] = word; }

    public int size() { return memory.length; }
}
