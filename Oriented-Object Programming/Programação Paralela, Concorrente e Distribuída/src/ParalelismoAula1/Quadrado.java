package ParalelismoAula1;

import java.util.concurrent.*;

public class Quadrado extends Thread {

    private int[] vetor;
    private int inicio, fim;
    private Semaphore termino;

    public Quadrado(int[] vetor, int inicio, int fim, Semaphore termino) {
        this.vetor = vetor;
        this.inicio = inicio;
        this.fim = fim;
        this.termino = termino;
    }

    public void run() {
        System.out.println("Início de quadrado de " + inicio + " a " + fim);
        for (int i = inicio; i <= fim; i++) {
            vetor[i] = (int) Math.pow(vetor[i], 2); // eleva ao quadrado
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fim de quadrado de " + inicio + " a " + fim);
        termino.release();
    }
}
