
package BarbeariaDeHilzer;

import static BarbeariaDeHilzer.Barbearia.DEMORA;
import static BarbeariaDeHilzer.Barbearia.INTERVALO;
import static BarbeariaDeHilzer.Barbearia.TMIN;
import static BarbeariaDeHilzer.Barbearia.filaCadeiras;
import static BarbeariaDeHilzer.Barbearia.filaSofa;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbeiro extends Thread{
    
    
    Semaphore mutex, barbeiro, clienteFilaSofa, 
              clienteFilaCadeiras, pagamento, recebimento;
    public static int id;
    private static final int ATRASO = DEMORA;
    private final Random periodo = new Random();
    private Semaphore sem;  // semáforo holder de outro semáforo.
    
    
    public Barbeiro(int id, Semaphore mutex, Semaphore barbeiro, 
            Semaphore clienteFilaSofa, Semaphore clienteFilaCadeiras,
            Semaphore pagamento, Semaphore recebimento){
        
        Barbeiro.id = id;
        this.mutex = mutex;
        this.barbeiro = barbeiro;
        this.clienteFilaSofa = clienteFilaSofa;
        this.clienteFilaCadeiras = clienteFilaCadeiras;
        this.pagamento = pagamento;
        this.recebimento = recebimento;
        
    }
    
     
    public void run(){
        
        Thread.currentThread().setName(String.format("%d",Barbeiro.id));   
        
        while(true){
            
            try{
                
                clienteFilaSofa.acquire();
                mutex.acquire();
                
                    sem = filaSofa.remover();
                    
                mutex.release();
                
                try{
                    sem.release(); //NullPointerException <- tratamento;
                           // quando dá return null do método remover!
                }
                catch(Exception fs){ fs.getSuppressed(); }
                
                clienteFilaCadeiras.acquire();
                mutex.acquire();
                
                    sem = filaCadeiras.remover();
                
                mutex.release();
                
                try{
                    sem.release(); //NullPointerException <- tratamento;
                           // quando dá return null do método remover!
                }
                catch(Exception fc){ fc.getSuppressed(); }
                
                barbeiro.release();  
                      
                cortarCabelo(Thread.currentThread().getName());
                              
                pagamento.acquire();
                recebimento.release();
               
            } 
            catch(InterruptedException e){ e.printStackTrace(); }
                 
        }
    
    }
    
    private void cortarCabelo(String n) throws InterruptedException{
        System.out.println("O barbeiro "+n+" está cortando o cabelo ...");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO)*ATRASO);
    }
    
}
