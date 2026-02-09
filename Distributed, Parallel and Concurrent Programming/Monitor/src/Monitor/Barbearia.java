
package Monitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock; 

public class Barbearia {

    
    private static int barbeiro = 0, cadeira = 0, aberta = 0;
    
    private final Lock mutex = new ReentrantLock();
    private final Condition barbeiro_disponivel = mutex.newCondition(),
                            cadeira_ocupada = mutex.newCondition(),
                            porta_aberta = mutex.newCondition(),
                            cliente_saiu = mutex.newCondition();
    
    public void corte_cabelo() throws InterruptedException{
        
        mutex.lock();
        try{
            
            while(barbeiro == 0){ barbeiro_disponivel.await(); }
            barbeiro--;
            cadeira++;
            cadeira_ocupada.signal();
            while(aberta == 0){ porta_aberta.await(); }
            aberta--;
            cliente_saiu.signal();
        
        }
        finally{
            mutex.unlock();
        }
        
    }
      

    public void pegue_proximo_cliente() throws InterruptedException{
       
        mutex.lock();
        try{
            
            barbeiro++;
            barbeiro_disponivel.signal();
            while(cadeira == 0){ cadeira_ocupada.await();}
            cadeira--;

        }
        finally{
            mutex.unlock();
        }
        
    }
    
    public void termine_corte() throws InterruptedException{
        
        mutex.lock();
        try{
            
            aberta++;
            porta_aberta.signal();
            while(aberta > 0){ cliente_saiu.await();}

        }
        finally{
            mutex.unlock();
        }
        
    }
    
    
}
