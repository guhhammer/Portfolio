
package Selvagens;

import static Selvagens.Tribo.INTERVALO;
import static Selvagens.Tribo.TMIN;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cozinheiro extends Thread{
    
    
    Semaphore poteVazio, poteCheio;
    public Random periodo = new Random();
    
    public Cozinheiro(Semaphore pVazio, Semaphore pCheio){
        
        this.poteVazio = pVazio;
        this.poteCheio = pCheio;
        
    }
    
    
    public void run(){
        
        try{
            
            while(true){
            
                poteVazio.acquire();
                cozinheiroEncheu();
                poteCheio.release();
            
            }
            
        }
        catch(InterruptedException e){  e.printStackTrace(); }
        
    }
    
    public void cozinheiroEncheu() throws InterruptedException{
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
        System.out.println("\nCozinheiro encheu o pote.\n");
    }
    
}
