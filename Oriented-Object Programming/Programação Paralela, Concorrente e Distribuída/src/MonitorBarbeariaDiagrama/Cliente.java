
package MonitorBarbeariaDiagrama;

import static MonitorBarbeariaDiagrama.CorCliente.VERDE;
import java.util.Random;


public class Cliente extends Thread {    

    private static int TMIN = 1000, TMAX = 4000, INTERVALO = TMAX -TMIN;
    private static Random periodo = new Random();
    
    
    private static int ID;
    private Barbearia barbearia;
    private static CorCliente cor = VERDE;
    
    
    public Cliente(int id, Barbearia b){
        this.ID = id;
        this.barbearia = b;
    }

    
    static int getID(){ return ID;}

    static void setColor(CorCliente c){ cor = c;}
    static CorCliente getColor(){ return cor;}
    
    static void spendTime() throws InterruptedException{ 
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
    }
    
    public void run(){
        
        while(true){
            
            try{
                Thread.currentThread().setName(String.format("%d",ID));
                barbearia.corte_cabelo(this); 
                spendTime();
            
                switch(Cliente.getColor()){
                    case VERDE:
                        spendTime();
                        break;
                    case AMARELO:
                        spendTime();
                        break;

                    case VERMELHO:
                        spendTime();
                        break;

                    case AZUL:
                        spendTime();
                        spendTime();
                        spendTime();
                        break;

                    case LARANJA:
                        spendTime();
                        break;

                }
                
            }
            catch(InterruptedException e2){ e2.getSuppressed(); }
        
        }
         
    }

}
