package Filosofos;

import java.util.Random;
import java.util.concurrent.*;

public class Filosofo extends Thread {

    private int ID;
    final private int TEMPO_MINIMO = 1000; // em milissegundos
    final private int TEMPO_MAXIMO = 5000; // em milissegundos
    final private int INTERVALO = TEMPO_MAXIMO - TEMPO_MINIMO;
    private Random periodo;

    Semaphore garfo_a_direita;
    Semaphore garfo_a_esquerda;
    Semaphore limitador;

    public Filosofo(int ID, Semaphore garfo_dir, Semaphore garfo_esq, Semaphore lim) {
        this.ID = ID;
        garfo_a_direita = garfo_dir;
        garfo_a_esquerda = garfo_esq;
        limitador = lim;
        periodo = new Random();
    }

    public void run() {
        while (true) {
            try {
                pense();
                pegue_os_garfos();
                coma();
                devolva_os_garfos();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pense() throws InterruptedException {
        int tempo = TEMPO_MINIMO + periodo.nextInt(INTERVALO);
        //System.out.println("Filosofo " + ID + " pensando por " + tempo + " milissegundos ...");
        Thread.sleep(tempo);
    }

    private void coma() throws InterruptedException {
        int tempo = TEMPO_MINIMO + periodo.nextInt(INTERVALO);
        System.out.println("Filosofo " + ID + " comendo por " + tempo + " milissegundos ...");
        Thread.sleep(tempo);
        System.out.println("Filosofo " + ID + " terminou de comer.");
    }

    private void pegue_os_garfos() throws InterruptedException {
        //System.out.println("Filosofo " + ID + " pegando os garfos");
        limitador.acquire();
        garfo_a_direita.acquire();
        garfo_a_esquerda.acquire();
    }

    private void devolva_os_garfos() {
        //System.out.println("Filosofo " + ID + " devolvendo os garfos");
        garfo_a_direita.release();
        garfo_a_esquerda.release();
        limitador.release();
    }
}
