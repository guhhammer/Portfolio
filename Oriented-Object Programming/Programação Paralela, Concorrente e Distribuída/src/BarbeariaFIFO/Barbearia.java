
package BarbeariaFIFO;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbearia{
    
    public static int TMIN = 1000;   // Tempo mínimo.
    public static int TMAX = 3000;   // Tempo máximo.
    public static int INTERVALO = TMAX - TMIN;
    private static final Random periodo = new Random(); 
    
    // O atraso do barbeiro em cortar o cabelo. (x vezes o normal.)
    public static int DEMORA = 4; 

    
    public static int capacidade = 4;
    public static int contadorClientes = 0;
    public static int ListaDeClientes = 0;
    
    
    public static FILA fila = new FILA(capacidade);
    

    private static void setListaDeClientes(int n){ 
        Barbearia.ListaDeClientes = n;
    }
    
    
    public static void main(String[] args) throws InterruptedException{
        
        setListaDeClientes(15);
        
        Semaphore mutex = new Semaphore(1),
                  cliente = new Semaphore(0),
                  clienteSatisfeito = new Semaphore(0),
                  corteConcluido = new Semaphore(0);
        
        Barbeiro b = new Barbeiro(mutex, cliente, 
                                      clienteSatisfeito, corteConcluido);
        
        b.start();
        
        for(int i = 0; i < ListaDeClientes; i++){
            
            Cliente c = new Cliente(i, mutex, cliente,
                                    clienteSatisfeito, corteConcluido);
                  
            Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
            c.start();
            Thread.sleep(TMIN);
            
        }
       
    }
    
} 