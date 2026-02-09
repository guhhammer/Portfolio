
package BarbeariaFIFO;

import static BarbeariaFIFO.Barbearia.DEMORA;
import static BarbeariaFIFO.Barbearia.INTERVALO;
import static BarbeariaFIFO.Barbearia.TMIN;
import static BarbeariaFIFO.Barbearia.fila;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread{
    
    Semaphore mutex, cliente, clienteSatisfeito, corteConcluido;
    private static final int ATRASO = DEMORA;
    private final Random periodo = new Random();
    private Semaphore sem; // semáforo holder de outro semáforo.
    
    
    public Barbeiro(Semaphore mutex, Semaphore cliente,
                    Semaphore clienteSatisfeito, Semaphore corteConcluido){
        
        this.mutex = mutex;
        this.cliente = cliente;
        this.clienteSatisfeito = clienteSatisfeito;
        this.corteConcluido = corteConcluido;
    
    }
    
    
    public void run(){
        
        while(true){
            
            try{
         
                cliente.acquire();
               
                mutex.acquire();
                   
                   sem = fila.remover();
                   
                mutex.release();
                
                try{
                    
                    cortarCabelo();
                    sem.release(); //NullPointerException <- tratamento;
                        // quando dá return null do método remover!
                }              
                catch(Exception n){ n.getSuppressed();}
               
                clienteSatisfeito.acquire();
                corteConcluido.release();
                  
            } 
            catch(InterruptedException e){ e.printStackTrace(); }
         
        }
        
    }
    
    private void cortarCabelo() throws InterruptedException{
        System.out.println("O barbeiro está cortando o cabelo ...");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO)*ATRASO);
    }
    
}
