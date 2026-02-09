
package BarbeariaFIFO;

import static BarbeariaFIFO.Barbearia.INTERVALO;
import static BarbeariaFIFO.Barbearia.TMIN;
import static BarbeariaFIFO.Barbearia.contadorClientes;
import static BarbeariaFIFO.Barbearia.fila;
import static BarbeariaFIFO.Barbearia.ListaDeClientes;
import static BarbeariaFIFO.Barbearia.capacidade;
import java.util.Random;
import java.util.concurrent.Semaphore; 

public class Cliente extends Thread{
    
    public static int id;
    Semaphore mutex, cliente, clienteSatisfeito, corteConcluido;
    public static Semaphore semaforo[] = new Semaphore[ListaDeClientes];
    
    private final Random periodo = new Random();
    
    
    public Cliente(int id, Semaphore mutex, Semaphore cliente, 
                   Semaphore clienteSatisfeito, Semaphore corteConcluido){
        
        Cliente.id = id;
        this.mutex = mutex;
        this.cliente = cliente;
        this.clienteSatisfeito = clienteSatisfeito;
        this.corteConcluido = corteConcluido;
        
    }
    
    public void run(){
        
        try{
            
            semaforo[Cliente.id] = new Semaphore(0);
            Thread.currentThread().setName(String.format("%d", Cliente.id));
            
            chegar();
            
            mutex.acquire();
                
                if(contadorClientes == capacidade){
                    
                    mutex.release();
                    desistir();
                    
                    try{  this.stop(); }
                    catch(Exception ex){ ex.printStackTrace(); }

                }
                
                contadorClientes += 1;
                
                fila.inserir(semaforo[Cliente.id]);
                
            mutex.release();
            
            
            cliente.release();
            semaforo[Cliente.id].acquire();
            
         
            terCabeloCortado(Thread.currentThread().getName());
            
            clienteSatisfeito.release();
            corteConcluido.acquire();
            
            
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
    
}
