
package Barbearia;

import static Barbearia.Barbearia.INTERVALO;
import static Barbearia.Barbearia.TMIN;
import static Barbearia.Barbearia.capacidade;
import static Barbearia.Barbearia.contadorClientes;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Cliente extends Thread{
    
    
    private int id;
    private Random periodo = new Random();
   
    
    Semaphore mutex, cliente, barbeiro; 
    Semaphore clienteSatisfeito, corteConcluido;
    
    public Cliente(int id, Semaphore mutex, 
            Semaphore cliente, Semaphore barbeiro,
            Semaphore satisfeito, Semaphore concluido){
        
        this.id = id;
        this.mutex = mutex; 
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.clienteSatisfeito = satisfeito;
        this.corteConcluido = concluido;
        
    }
    
    
    public void run(){
        
        try {
            
            chegar();
            
            mutex.acquire();
                
                if(contadorClientes == capacidade){
                    
                    mutex.release();
                    
                    desistir();
                    
                    try{ this.stop(); }
                    catch(Exception ex){ ex.printStackTrace(); }
                
                }
                
                contadorClientes += 1;
            
            mutex.release();
            
            
            cliente.release();
            barbeiro.acquire();
            
            terCabeloCortado();
            
            clienteSatisfeito.release();
            corteConcluido.acquire();
                        
            
            mutex.acquire();
                
                contadorClientes -= 1;
                
            mutex.release();
            
        }
        catch(InterruptedException e){  e.printStackTrace(); }
    
    }
    
    public void chegar(){
        System.out.println("\nCliente "+id+" chegou.\n");
    }
    
    public void desistir(){
        System.out.println("\nCliente "+id+" desistiu.\n");
    }
    
    public void terCabeloCortado() throws InterruptedException{
        System.out.println("Cliente "+id+" está tendo o seu cabelo cortado ...");
        Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
        System.out.println("Cliente "+id+" cortou o seu cabelo.");
    }
    
}
