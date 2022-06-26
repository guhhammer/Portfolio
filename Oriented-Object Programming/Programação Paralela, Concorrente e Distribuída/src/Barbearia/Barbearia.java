
package Barbearia;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbearia {
    
    
    public static int TMIN = 1000; // Tempo mínimo;
    public static int TMAX = 1500; // Tempo máximo;
    public static int INTERVALO = TMAX - TMIN;
    
    public static int capacidade = 4;
    public static int contadorClientes = 0;
    
    
    public static void main(String[] args) throws InterruptedException {
     
        
        int ClientesLista = 10;
        Random periodo = new Random();
        
        Semaphore mutex = new Semaphore(1);
        
        Semaphore cliente = new Semaphore(0);
        
        Semaphore barbeiro = new Semaphore(0);
        
        Semaphore clienteSatisfeito = new Semaphore(0);
        
        Semaphore corteConcluido = new Semaphore(0);
        
        Barbeiro b = new Barbeiro(cliente, barbeiro, 
                                clienteSatisfeito, corteConcluido);
        b.start();
        
        for(int i = 0; i < ClientesLista; i++){
            Cliente c = new Cliente(i, mutex, cliente, barbeiro, 
                    clienteSatisfeito, corteConcluido);
            Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
            c.start();
        }
        
    }
    
    
}
