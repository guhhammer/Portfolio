
package Controladoras;

import java.util.concurrent.Semaphore;

public abstract class Carro extends Thread{
    
    
    Semaphore mutex, Cheguei, PermissaoIr;
    static int contador = Main.contador;
    static int ID;
    
    public Carro(int ID, Semaphore mutex, Semaphore Cheguei, Semaphore PIR){
        
        this.ID = ID;
        this.mutex = mutex;
        this.Cheguei = Cheguei;
        this.PermissaoIr = PIR;
    
    }
    
    public void run(){
    
        try{
        
            mutex.acquire(); contador += 1; mutex.release();
            
            Cheguei.release(); chegou(); PermissaoIr.acquire();
            
            mutex.acquire(); contador -= 1; mutex.release();
            
        }
        catch(Exception e){ e.printStackTrace(); } 
    
    }
    
    protected static void chegou(){
        System.out.println(String.format("\nCarro %d chegou.\n", ID));
    }
    
    
}
