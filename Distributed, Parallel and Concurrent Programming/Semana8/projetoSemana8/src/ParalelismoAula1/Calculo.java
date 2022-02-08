package ParalelismoAula1;

import java.util.Random;

public class Calculo {

    private long tamSequencia;

    public Calculo(long tamSequencia) {
        this.tamSequencia = tamSequencia;
    }

    public double execute() {
        Random gerador = new Random();
        double total = 0.0;
        for (int i = 0; i < tamSequencia; i++) {
            int k = gerador.nextInt(10000);
            double r = Math.sqrt(k);
            total = total + r;
        }
        return total;
    }
}
