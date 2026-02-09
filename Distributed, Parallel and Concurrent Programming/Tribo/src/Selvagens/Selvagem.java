
package Selvagens;

import static Selvagens.Tribo.INTERVALO;
import static Selvagens.Tribo.TMIN;
import static Selvagens.Tribo.porcoesDisponiveis;
import static Selvagens.Tribo.quantidadeCozinheiroColoca;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Selvagem extends Thread{
    
    public int id;
    Semaphore mutex, poteVazio, poteCheio;
    public Random periodo = new Random();
    public int DESCANSO = INTERVALO/2; //tempo de descanso pós refeição(Delay).
    
    public Selvagem(int id, Semaphore mutex, Semaphore pVazio, Semaphore pCheio){
        
        this.id = id;
        this.mutex = mutex;
        this.poteVazio = pVazio;
        this.poteCheio = pCheio;
    
    }
    
    public void run(){
        
        while(true){
        
            try{
                
                mutex.acquire();
                
                if(porcoesDisponiveis == 0){
                    
                    poteVazio.release();
                    poteCheio.acquire();
                    porcoesDisponiveis = quantidadeCozinheiroColoca;
                    
                }
                servirSeDoPote();
                
                mutex.release();
                comer();
                
            }
            catch(InterruptedException e){ e.printStackTrace();}
        }
        
    }
    
    public void servirSeDoPote() throws InterruptedException{
        porcoesDisponiveis -= 1;
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO)+DESCANSO);
    }
    
    public void comer() throws InterruptedException{
        System.out.println("Selvagem "+id+" serviu-se do pote. "
                     + "(Quantidade Disponível:  "+porcoesDisponiveis+" )");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO)+DESCANSO);
    }
    
}
