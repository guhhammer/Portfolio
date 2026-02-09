
package Trabalho01;

import static Trabalho01.Inicio.*;
import java.util.concurrent.Semaphore;

public class Analisador extends Thread {
    

    private Semaphore mutex, minhaVez, vezProximo;

    public Analisador(Semaphore mutex, Semaphore flag4, Semaphore flag3){
        
        this.mutex = mutex;
        this.minhaVez = flag4;
        this.vezProximo = flag3;
    
    }

    static final int ascii = 256;

    static void ocorrencias(String aux){

        int count[] = new int[ascii];
        int len = aux.length();

        for(int i = 0; i < len; i++){ count[aux.charAt(i)]++; }

        char hold[] = new char[aux.length()];
        
        for(int i = 0; i < len; i++){
            hold[i] = aux.charAt(i);
            
            int find = 0;
            for(int j = 0; j <= i; j++){ if(aux.charAt(i)==hold[j]){ find++; }}
            
            if(find==1){
                System.out.println("\tNº de Ocorrências ("+aux.charAt(i)+
                                            ") é:   "+count[aux.charAt(i)]);
            }
            
        }
        
    }
    
    
    public void run(){
        
        while(true){
            
            try{
                
                minhaVez.acquire();
                
                mutex.acquire();
                
                System.out.println("Ocorrências: ");
                ocorrencias(bufferFA);     // Imprime as ocorrências.
                
                mutex.release();
                
                vezProximo.release();
                
                Thread.sleep(sleepTimeThread);
                
            }
            
            catch(InterruptedException e){ e.printStackTrace(); }
            
        }
        
    }

}
