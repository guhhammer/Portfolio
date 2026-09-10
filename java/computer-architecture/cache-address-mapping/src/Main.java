/** Splits a memory address into the tag / line / word fields of a direct-mapped cache
 *  (8K-word cache, 64-word lines, 16M-word main memory). Computer Architecture course, PUCPR (2020). */
public class Main {

    /** The top s bits of the address (tag + line), i.e. the address with its word bits cleared. */
    public static int blockIndex(int address, int s) {
        String bits = Integer.toBinaryString(address);
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) {
            ret.append(i < s ? bits.charAt(i) : '0');
        }
        return Integer.parseInt(ret.toString(), 2);
    }

    /** Number of bits needed to represent the address. */
    public static int addressBits(int address) {
        int count = 0;
        while (address > 0) { address >>= 1; count++; }
        return count;
    }

    /** The low word-offset bits of the address. */
    public static int wordOffset(int address, int wordBits) {
        return address & ((1 << wordBits) - 1);
    }

    public static void main(String[] args) {
        int wordsPerLine = 64,
            cacheSize = (int) (8 * Math.pow(2, 10)),     // 8K words
            memorySize = (int) (16 * Math.pow(2, 20));   // 16M words

        int address = 10560325;

        int wordBits = (int) (Math.log10(wordsPerLine) / Math.log10(2));                                 // w
        int lineBits = (int) (Math.log10((int) (cacheSize / Math.pow(2, wordBits))) / Math.log10(2));    // r
        int tagBits = Integer.toBinaryString(address).length() - lineBits - wordBits;                    // t
        int s = tagBits + lineBits;

        int blockStart = blockIndex(address, s);
        int blockEnd = blockStart + wordsPerLine;

        System.out.println("address       = " + address + " (" + addressBits(address) + " bits; memory of " + memorySize + " words)");
        System.out.println("word bits (w) = " + wordBits + ", line bits (r) = " + lineBits + ", tag bits (t) = " + tagBits);
        System.out.println("word offset   = " + wordOffset(address, wordBits));
        System.out.println("block         = [" + blockStart + ", " + blockEnd + ")");
    }
}
