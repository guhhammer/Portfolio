
package BarbeariaDeHilzer;

import static BarbeariaDeHilzer.Barbearia.INTERVALO;
import static BarbeariaDeHilzer.Barbearia.TMIN;
import static BarbeariaDeHilzer.Barbearia.capacidadeTotal;
import static BarbeariaDeHilzer.Barbearia.contadorClientes;
import static BarbeariaDeHilzer.Barbearia.filaCadeiras;
import static BarbeariaDeHilzer.Barbearia.filaSofa;
import static BarbeariaDeHilzer.Barbearia.listaDeClientes;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread{
    
    
    public static int id;
    Semaphore mutex, sofa, barbeiro, clienteFilaSofa, clienteFilaCadeiras,
              pagamento, recebimento;
    
    public static Semaphore representanteFilaSofa[] = 
                                    new Semaphore[listaDeClientes];
    public static Semaphore representanteFilaCadeiras[] =
                                    new Semaphore[listaDeClientes];
    
    
    private final Random periodo = new Random();
    
    public Cliente(int id, Semaphore mutex, Semaphore sofa, Semaphore barbeiro,
                   Semaphore clienteFilaSofa, Semaphore clienteFilaCadeiras,
                   Semaphore pagamento, Semaphore recebimento){
        
        Cliente.id = id;
        this.mutex = mutex;
        this.sofa = sofa;
        this.barbeiro = barbeiro;
        this.clienteFilaSofa = clienteFilaSofa;
        this.clienteFilaCadeiras = clienteFilaCadeiras;
        this.pagamento = pagamento;
        this.recebimento = recebimento;
        
    }
    
    
    
    public void run(){ 
        
        try{
            
            representanteFilaSofa[Cliente.id] = new Semaphore(0); 
            representanteFilaCadeiras[Cliente.id] = new Semaphore(0);
            Thread.currentThread().setName(String.format("%d", Cliente.id));
            
            chegar();
            
            mutex.acquire();
            
                //stats();
            
                if(contadorClientes == capacidadeTotal){

                    mutex.release();
                    desistir();

                    try{ this.stop();}
                    catch(Exception ex){ ex.printStackTrace(); }

                }
                
                contadorClientes += 1;
                filaSofa.inserir(representanteFilaSofa[(Cliente.id)]);

            mutex.release();
            
            clienteFilaSofa.release();
            representanteFilaSofa[(Cliente.id)].acquire();

            sofa.acquire();

                mutex.acquire();

                    filaCadeiras.inserir(representanteFilaCadeiras[Cliente.id]);

                mutex.release();

                clienteFilaCadeiras.release();
                
                try{
                    representanteFilaCadeiras[Cliente.id].acquire();
                }
                catch(Exception rc){  rc.getSuppressed(); }
                
            sofa.release();
            
            terCabeloCortado(Thread.currentThread().getName());
            
            pagamento.release();
            recebimento.acquire();
            
            mutex.acquire();

                contadorClientes -= 1;
            
            mutex.release();       
                
        }
        catch(InterruptedException e){ e.printStackTrace(); }
        
    }
    
    private void chegar(){
        System.out.println("\nCliente "+id+" chegou.\n");
    }
     
    private void desistir(){
        System.out.println("\nCliente "+id+" desistiu.\n");
    }
    
    private void terCabeloCortado(String n) throws InterruptedException{
        System.out.println("Cliente "+n+" está cortando ...");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
        System.out.println("Cliente "+n+" cortou o cabelo. ");
    }
    
    private void stats(){
        System.out.println(
                "Cont.: "+contadorClientes+
                " | Capac.: "+capacidadeTotal+
                " | (Cont. < Capac.): "+(contadorClientes<capacidadeTotal));
    }
    
}
