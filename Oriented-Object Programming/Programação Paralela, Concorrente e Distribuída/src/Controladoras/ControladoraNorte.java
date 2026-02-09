
package Controladoras;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ControladoraNorte extends Controladora{
    
    public ControladoraNorte(Semaphore mutex, Semaphore Cheguei, Semaphore PIR,
                           Semaphore CN, Semaphore CS, int contador){
        super(mutex, Cheguei, PIR, CN, CS, contador);
    }
    
    public void run(){
        
        
        try{
            
            CN.acquire();
            tempo = 0;
            contadorTotal = 0;
            
            Main.CtrlAtual = "Norte";
            controladoraAtual();
            
            ordemChegadaNorte.add(Carro.ID);
            
            if(!cheia(listaIDSCarros)){
                    inserir((int) ordemChegadaNorte.get(0));
                    ordemChegadaNorte.remove(0);
            }
            
            mutex.acquire();
            
            while(contadorTotal < 10){
                
                if(!cheia(listaIDSCarros)){
                    inserir((int) ordemChegadaNorte.get(0));
                    ordemChegadaNorte.remove(0);
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
            
            CS.release();
            
        } 
        catch(Exception e){ e.printStackTrace(); }
     
        
        
    }
}
