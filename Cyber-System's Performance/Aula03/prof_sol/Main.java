package pscf;

public class Main {

    public static void main(String[] args) throws InvalidAddress {
        IO es = new IO(System.out);

        RAM ram = new RAM(128);
        ram.Set(10,120);
        ram.Set(11,127);

        CPU cpu = new CPU(ram, es);
        cpu.Run(10);
    }
}
