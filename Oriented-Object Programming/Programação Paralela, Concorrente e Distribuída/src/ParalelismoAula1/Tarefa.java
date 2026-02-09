package ParalelismoAula1;

import java.util.Random;
import java.util.concurrent.*;

public class Tarefa extends Thread {

    private int ID;
    private long tamSequencia;
    private Semaphore conclusao;

    public Tarefa(int ID, long tamSequencia, Semaphore conclusao) {
        this.ID = ID;
        this.tamSequencia = tamSequencia;
        this.conclusao = conclusao;
    }

    public void run() {
        Calculo c = new Calculo(tamSequencia);
        c.execute();
        conclusao.release();
    }
}
