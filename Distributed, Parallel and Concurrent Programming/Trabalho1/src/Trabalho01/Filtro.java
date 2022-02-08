
package Trabalho01;

import static Trabalho01.Inicio.*;
import java.util.concurrent.Semaphore;

public class Filtro extends Thread{
    
    private Semaphore mutexG,mutexA, minhaVezG, vezProximoG, minhaVezA, vezProximoA;
    
    public Filtro(Semaphore mutexG,Semaphore mutexA, Semaphore flag2, Semaphore flag1,
                                   Semaphore flag3, Semaphore flag4){
        
        this.mutexA = mutexA;
        this.mutexG = mutexG;
        this.minhaVezG = flag2;
        this.vezProximoG = flag1;
        this.minhaVezA = flag3;
        this.vezProximoA = flag4;
        
    
    }
    
    void setBuffer(String aux){ bufferFA = aux; }

    public void run(){
       
        while(true){
        
            try{
                
                minhaVezG.acquire();
                minhaVezA.acquire();
                
                mutexG.acquire();
                mutexA.acquire();
                
                setBuffer(bufferGF.toUpperCase()); // Lê e, depois, escreve.
                System.out.println("Filtro: \t"+bufferFA);
                
                mutexG.release();
                mutexA.release();
                
                vezProximoA.release();
                vezProximoG.release();
                
                Thread.sleep(sleepTimeThread);
                
            } 
            
            catch(InterruptedException e){ e.printStackTrace(); }
        
        }
        
    }
    
}
