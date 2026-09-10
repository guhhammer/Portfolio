/** A 16-word cache in front of a 128-word RAM; the CPU walks the range [120, 127] through the cache.
 *  Computer Architecture course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) {
        IO io = new IO(System.out);

        RAM ram = new RAM(128);
        ram.set(10, 120);
        ram.set(11, 127);

        Cache cache = new Cache(16, ram);
        CPU cpu = new CPU(cache, io);
        cpu.run(10);
    }
}
