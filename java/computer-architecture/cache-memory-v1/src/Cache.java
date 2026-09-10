/** A single-block cache in front of RAM: a miss reloads `size` consecutive words starting at the missed address.
 *  Writes go through to RAM and refresh the cached copy when the address is resident. */
public class Cache extends Memory {

    private final RAM ram;
    private final int[] addresses;   // which RAM address each cache slot currently holds

    public Cache(int size, RAM ram) {
        super(size);
        this.ram = ram;
        this.addresses = new int[size];
    }

    private int missed(int address) {
        for (int i = 0; i < size(); i++) {
            addresses[i] = (address + i) % ram.size();
            super.set(i, ram.get(address + i));
        }
        return ram.get(address);
    }

    @Override
    public int get(int address) {
        int slot = address - addresses[0];
        if (slot < 0 || slot > size() - 1) { return missed(address); }
        boolean hit = (addresses[slot] == address);
        return hit ? super.get(slot) : missed(address);
    }

    @Override
    public void set(int address, int word) {
        ram.set(address, word);                                     // write-through.
        int slot = address - addresses[0];
        if (!(slot < 0 || slot > size() - 1)) { super.set(slot, ram.get(address)); }
    }
}
