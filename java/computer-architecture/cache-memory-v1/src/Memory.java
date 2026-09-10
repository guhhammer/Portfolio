/** Base word-addressed memory. */
public class Memory {

    protected final int[] memory;
    private final int size;

    public Memory(int size) {
        this.size = size;
        this.memory = new int[size];
    }

    public int size() { return size; }

    public int get(int address) { return memory[address % size]; }

    public void set(int address, int word) { memory[address] = word; }
}
