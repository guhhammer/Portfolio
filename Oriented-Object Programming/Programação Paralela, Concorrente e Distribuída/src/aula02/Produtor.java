package aula02;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Produtor extends Thread {

    private int[] buffer;
    private Semaphore mutex, itens, espacos;

    public Produtor(int[] buffer, Semaphore mutex,
            Semaphore itens, Semaphore espacos) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
    }

    public void run() {
        Random gerador = new Random();
        while (true) {
            try {
                int k = gerador.nextInt(1000); // aleatório entre 0 e 999
                espacos.acquire();
                mutex.acquire();
                buffer[Inicio.ultimo] = k;
                Inicio.ultimo = (Inicio.ultimo + 1) % 100;
                System.out.println("\nProduzido: " + k);
                mutex.release();
                itens.release();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
