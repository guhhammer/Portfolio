package aula01;

import java.util.concurrent.*;

public class Teste02 {

    public static void main(String[] args) {

        Semaphore s = new Semaphore(1);

        try {
            teste(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("fim do teste");
    }

    private static void teste(Semaphore x) throws InterruptedException {
        System.out.println("esperando ...");
        x.acquire();
        System.out.println("pego");
        x.release();
        System.out.println("liberado");
    }
}
