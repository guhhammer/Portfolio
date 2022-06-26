package aula01;

import java.util.concurrent.*;

public class Teste03 {

    public static void main(String[] args) {

        Semaphore s = new Semaphore(1);

        Tarefa c1 = new Tarefa(1, s);
        c1.start();

        Tarefa c2 = new Tarefa(2, s);
        c2.start();
    }
}
