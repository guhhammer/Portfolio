package aula02;

import java.util.concurrent.Semaphore;

public class Inicio {

    static int primeiro = 0;
    static int ultimo = 0;

    public static void main(String[] args) {

        int[] buffer = new int[100];

        Semaphore mutex = new Semaphore(1);
        Semaphore itens = new Semaphore(0);
        Semaphore espacos = new Semaphore(100);

        Consumidor c1 = new Consumidor(buffer, mutex, itens, espacos);
        Consumidor c2 = new Consumidor(buffer, mutex, itens, espacos);
        Produtor p1 = new Produtor(buffer, mutex, itens, espacos);
        Produtor p2 = new Produtor(buffer, mutex, itens, espacos);

        c1.start();
        c2.start();
        p1.start();
        p2.start();
    }

}
