package memory;

import exception.InvalidAddress;

/** Direct-mapped, write-back cache. A 24-bit address is split into
 *  tag (bits 0-10), line (bits 11-17) and word (bits 18-23) fields;
 *  the block field (tag + line) is used as the RAM base address of the block. */
public class Cache extends AbstractMemory {

    private final CacheLine[] lines;
    private final int lineCount;
    private final int wordsPerLine = 64;
    private final RAM ram;

    public Cache(int size, RAM ram) {
        this.lineCount = size / wordsPerLine;              // number of cache lines.
        this.lines = new CacheLine[lineCount];
        this.ram = ram;
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new CacheLine(wordsPerLine, i);     // tags start as if RAM held nothing.
        }
    }

    private static String pad24(int address) {
        StringBuilder bits = new StringBuilder(Integer.toBinaryString(address));
        while (bits.length() < 24) { bits.insert(0, '0'); }
        return bits.toString();
    }

    private int wordField(int address)  { return Integer.parseInt(pad24(address).substring(18, 24), 2); }
    private int lineField(int address)  { return Integer.parseInt(pad24(address).substring(11, 18), 2); }
    private int tagField(int address)   { return Integer.parseInt(pad24(address).substring(0, 11), 2); }
    private int blockField(int address) { return Integer.parseInt(pad24(address).substring(0, 18), 2); }

    public int size() { return lineCount; }

    /** Copies a dirty line back to main memory before it is replaced. */
    private void writeBack(int line, int lineTag) throws InvalidAddress {
        if (lines[line].isDirty()) {
            for (int i = 0; i < wordsPerLine; i++) {
                int position = Integer.parseInt(Integer.toBinaryString(lineTag)
                        + Integer.toBinaryString(line) + Integer.toBinaryString(i), 2);
                ram.set(position, lines[line].get(i));
            }
        }
    }

    /** Loads the block that contains `address` from main memory into its cache line. */
    private void fetch(int address, int line) throws InvalidAddress {
        int block = blockField(address);
        lines[line].setTag(tagField(ram.get(block)));      // update the tag.
        for (int i = 0; i < wordsPerLine; i++) {
            lines[line].set(i, ram.get(block + i));        // copy the block into the line.
        }
    }

    public int get(int address) throws InvalidAddress {
        int line = lineField(address), tag = tagField(address), word = wordField(address);
        int lineTag = lines[line].getTag();

        if (lineTag != tag) {                              // cache miss.
            writeBack(line, lineTag);
            fetch(address, line);
        }
        return lines[line].get(word);                      // cache hit.
    }

    public void set(int address, int word) throws InvalidAddress {
        int line = lineField(address), tag = tagField(address), offset = wordField(address);
        int lineTag = lines[line].getTag();

        if (lineTag != tag) {                              // cache miss.
            writeBack(line, lineTag);
            fetch(address, line);
        }
        lines[line].set(offset, word);                     // write into the line; RAM is updated on eviction.
    }
}
