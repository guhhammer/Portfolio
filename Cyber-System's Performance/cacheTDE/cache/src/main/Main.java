package main;

import cpu.CPU;
import exception.InvalidAddress;
import io.IO;
import memory.Cache;
import memory.RAM;

// ALUNOS:
// João Vitor Andrioli de Souza.
// Gustavo Hammerschmidt.

public class Main {

    public static void main(String[] args) throws InvalidAddress {

        IO io = new IO(System.out);
        RAM ram = new RAM(16000);

        Cache cache = new Cache(8192, ram); // 8192/64 = 128 CacheLines.
        cache.Set(10, 120);
        cache.Set(11, 127);

        CPU cpu = new CPU(cache, io);
        cpu.Run(10);

    }

}
