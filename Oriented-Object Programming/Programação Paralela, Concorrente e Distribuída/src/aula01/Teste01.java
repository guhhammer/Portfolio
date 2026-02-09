package aula01;

import java.util.concurrent.*;

public class Teste01 {

    public static void main(String[] args) {

        Semaphore s = new Semaphore(0);

        try {
            System.out.println("esperando ...");
            s.release();
            s.acquire(); // ESPERAR
            System.out.println("pego");
            s.release(); // SINALIZAR
            System.out.println("liberado");
            System.out.println("fim do teste");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
