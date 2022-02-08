
package Barbearia;


import static Barbearia.Barbearia.INTERVALO;
import static Barbearia.Barbearia.TMIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread{
    
    private Random periodo = new Random();
    
    Semaphore cliente, barbeiro;
    Semaphore clienteSatisfeito, corteConcluido;
    public int ATRASO = 8;
    
    public Barbeiro(Semaphore cliente, Semaphore barbeiro,
                    Semaphore clienteSatisfeito, Semaphore corteConcluido){
    
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.clienteSatisfeito = clienteSatisfeito;
        this.corteConcluido = corteConcluido;
        
    }
    
    
    public void run() {
       
        try {
            
            while(true){
                
                cliente.acquire();
                barbeiro.release();
                cortarCabelo();
                clienteSatisfeito.acquire();
                corteConcluido.release();
            
            }
            
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    
    }
    
    public void cortarCabelo() throws InterruptedException{
        System.out.println("\nBarbeiro corta o cabelo ...");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO)*ATRASO);
    }
    
    
}
