
package Selvagens;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Tribo {
    
    
    public static int TMIN = 1000; // Tempo mínimo;
    public static int TMAX = 1010; // Tempo máximo;
    public static int INTERVALO = TMAX - TMIN;
    
    public static int porcoesDisponiveis = 0;
    
    public static int quantidadeCozinheiroColoca = 50;
    
    public static void main(String[] args) throws InterruptedException{
        
        int populacao = 100;
        Random periodo = new Random();
        
        Semaphore mutex = new Semaphore(1);
        
        Semaphore poteVazio = new Semaphore(0);
        Semaphore poteCheio = new Semaphore(0);
        
        Cozinheiro c = new Cozinheiro(poteVazio, poteCheio);
        c.start();
        
        
        for(int i = 0; i < populacao; i++){
            Selvagem s = new Selvagem(i, mutex, poteVazio, poteCheio);
            Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
            s.start();
        }
        
    }
    
    
    
}
