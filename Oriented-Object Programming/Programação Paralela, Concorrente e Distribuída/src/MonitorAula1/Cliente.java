
package MonitorAula1;

import java.util.Random;

public class Cliente extends Thread {

    private int ID;
    private Barbearia barbearia;
     
    public Cliente(Barbearia b, int id){
        barbearia = b;
        ID = id;
    }

    public void run() {
     
        try{
            
            barbearia.corte_cabelo();
            System.out.println("Cliente "+ID+" cortou o cabelo.");
            Thread.sleep(new Random().nextInt(2000));
            
        } 
        catch(InterruptedException ex){ ex.printStackTrace(); }
   
    }

    
}
