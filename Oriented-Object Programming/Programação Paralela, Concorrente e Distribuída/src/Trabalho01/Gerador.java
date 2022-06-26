
package Trabalho01;

import static Trabalho01.Inicio.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Gerador extends Thread {
    
    public Semaphore mutex, minhaVez, vezProximo;
    
    public Gerador(Semaphore mutex, Semaphore flag1, Semaphore flag2){
        
        this.mutex = mutex;
        this.minhaVez = flag1;
        this.vezProximo = flag2;
        
    }
    
    void setBuffer(String aux){ bufferGF = aux; }
    
    public static String geradorString(Random random, int length){
        
        StringBuilder sb = new StringBuilder();
        
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        
        for(int i = 0; i < length; i++){
            sb.append(alpha.charAt(random.nextInt(alpha.length())));
        }

        return sb.toString();
    }

    
    public void run(){
        
        Random random = new Random();
        
        while(true){
            
            try{
                
                minhaVez.acquire();
                
                mutex.acquire();
                
                setBuffer(geradorString(random, 10));
                System.out.println("\n\nGerador: \t"+bufferGF);
                
                mutex.release();
                
                vezProximo.release();
                
                Thread.sleep(sleepTimeThread);
                
            } 
            
            catch(InterruptedException e){ e.printStackTrace(); }
            
        }
        
    }
   
}
