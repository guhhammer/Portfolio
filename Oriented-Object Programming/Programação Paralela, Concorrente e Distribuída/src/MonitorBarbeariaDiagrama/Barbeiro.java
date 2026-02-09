
package MonitorBarbeariaDiagrama;

import static MonitorBarbeariaDiagrama.CorBarbeiro.ROXO;

public class Barbeiro extends Thread{

    private Barbearia barbearia;
    private static CorBarbeiro cor = ROXO;
            
    public Barbeiro(Barbearia b){  this.barbearia = b; } 
    
    public static void setColor(CorBarbeiro cb){ cor = cb; }
    
    public static CorBarbeiro getColor(){ return cor; }
 
    
    public void run(){
        while(true){
            try {
                barbearia.pegue_proximo_cliente();
            } 
            catch(InterruptedException e){ e.printStackTrace(); }
        }
    }
    
    
}
