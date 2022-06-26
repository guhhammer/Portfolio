/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonitorSemCondição;

/**
 *
 * @author Gustavo
 */
public class Teste {

    /**
     * @param args
     */
    public static void main(String[] args) {

        BufferLimitado b = new BufferLimitado(10);
        Produtor p1 = new Produtor(b);
        Consumidor c1 = new Consumidor(b);
        p1.start();
        c1.start();
    }
}
