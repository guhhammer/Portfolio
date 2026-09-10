package main;

import cpu.CPU;
import exception.InvalidAddress;
import io.IO;
import memory.Cache;
import memory.RAM;

/** Direct-mapped write-back cache simulator: an 8192-word cache with 64-word lines (128 lines) over a 16000-word RAM.
 *  Computer Architecture course, PUCPR (2020). Team: Gustavo Hammerschmidt and João Vitor Andrioli de Souza. */
public class Main {

    public static void main(String[] args) throws InvalidAddress {
        IO io = new IO(System.out);
        RAM ram = new RAM(16000);

        Cache cache = new Cache(8192, ram);   // 8192 / 64 = 128 cache lines.
        cache.set(10, 120);
        cache.set(11, 127);

        CPU cpu = new CPU(cache, io);
        cpu.run(10);
    }
}
