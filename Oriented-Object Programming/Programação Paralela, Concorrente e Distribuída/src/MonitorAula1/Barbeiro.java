
package MonitorAula1;

import java.util.Random;

public class Barbeiro extends Thread{

    private Barbearia barbearia;
    
    public Barbeiro(Barbearia b){
        barbearia = b;        
    }
    
    public void run(){
        while(true){
            
            try{
                Thread.sleep(new Random().nextInt(1000));
                barbearia.pegue_proximo_cliente();
            
            } 
            catch(InterruptedException ex){ ex.printStackTrace(); }
            
            System.out.println("Barbeiro está cortando...\n");
            
            try{
                
                barbearia.termine_corte();
                Thread.sleep(new Random().nextInt(3000));
                
            } 
            catch(InterruptedException ex){ ex.printStackTrace(); }
            
            System.out.println("Barbeiro terminou de cortar.\n");
        }
    } 
    
}
