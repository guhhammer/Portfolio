/** Loads a tiny boot program into RAM and runs the CPU. Computer Architecture course, PUCPR (2020). */
public class Main {

    public static void main(String[] args) {
        RAM ram = new RAM(128);
        IO io = new IO(System.out, ram);
        CPU cpu = new CPU(ram, io);

        ram.set(0, 0x78);       // the boot area starts at 0x78...
        ram.set(1, 0x7E);       // ...and ends at 0x7E.

        ram.set(0x78, 0xA);     // boot "instructions".
        ram.set(0x79, 0x14);
        ram.set(0x7A, 0x15);
        ram.set(0x7B, 0x16);
        ram.set(0x7C, 0x17);
        ram.set(0x7D, 0xC);
        ram.set(0x7E, 0x1F);

        cpu.run();
    }
}
