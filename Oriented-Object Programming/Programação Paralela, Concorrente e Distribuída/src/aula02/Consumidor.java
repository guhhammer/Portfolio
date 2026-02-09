package aula02;

import java.util.concurrent.Semaphore;

public class Consumidor extends Thread {

    private int[] buffer;
    private Semaphore mutex, itens, espacos;

    public Consumidor(int[] buffer, Semaphore mutex,
            Semaphore itens, Semaphore espacos) {
        this.buffer = buffer;
        this.mutex = mutex;
        this.itens = itens;
        this.espacos = espacos;
    }

    public void run() {
        while (true) {
            try {
                itens.acquire();
                mutex.acquire();
                int k = buffer[Inicio.primeiro];
                Inicio.primeiro = (Inicio.primeiro + 1) % 100;
                System.out.println("Consumido: " + k);
                mutex.release();
                espacos.release();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
