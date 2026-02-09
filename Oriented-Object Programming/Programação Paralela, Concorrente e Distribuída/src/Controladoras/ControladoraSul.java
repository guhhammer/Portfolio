
package Controladoras;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ControladoraSul extends Controladora{

    public ControladoraSul(Semaphore mutex, Semaphore Cheguei, Semaphore PIR,
                           Semaphore CN, Semaphore CS, int contador){
        super(mutex, Cheguei, PIR, CN, CS, contador);
    }
        
    public void run(){
        
        try{
            
            CS.acquire();
            tempo = 0;
            contadorTotal = 0;
            
            Main.CtrlAtual = "Sul";
            controladoraAtual();
            
            ordemChegadaSul.add(Carro.ID);
            
            if(!cheia(listaIDSCarros)){
                    inserir((int) ordemChegadaSul.get(0));
                    ordemChegadaSul.remove(0);
            }
            
            mutex.acquire();
            
            while(contadorTotal < 10){
                
                if(!cheia(listaIDSCarros)){
                    inserir((int) ordemChegadaSul.get(0));
                    ordemChegadaSul.remove(0);
                }
                
                if(contador == 0){ break; }
                
                PermissaoIr.release();
                int idAtual = pegarProximo();
                temPermissao(idAtual);
                remover(listaIDSCarros, idAtual);
                contadorTotal++;
                
                tempoFim = System.currentTimeMillis();
            
                if(tempoFim-tempo == 60000){ break;}
                
            }
            
            mutex.release();
            
            CN.release();
            
        } 
        catch(Exception e){ e.printStackTrace(); }
     
        
    }
    
    
    
    
}
