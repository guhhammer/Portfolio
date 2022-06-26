package BarbeariaDeHilzer;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Barbearia {
    
    public static int TMIN = 1000;   // Tempo mínimo.
    public static int TMAX = 3000;   // Tempo máximo.
    public static int INTERVALO = TMAX - TMIN;
    private static final Random periodo = new Random();
    
    // O atraso do barbeiro em cortar o cabelo. (x vezes o normal.)
    public static int DEMORA = 5;    
    
    public final static int listaDeBarbeiros = 3;
    public final static int numeroDeCadeiras = listaDeBarbeiros;
    public final static int numeroDePessoasNoSofa = 4;
    public final static int limiteDeClientes = 13;
    public final static int capacidadeTotal = 
            (numeroDeCadeiras+numeroDePessoasNoSofa+limiteDeClientes); 
    
    
    public static int contadorClientes = 0;
    public static int listaDeClientes = 0;
    
    
    public static FILA filaSofa = new FILA(numeroDePessoasNoSofa);
    public static FILA filaCadeiras = new FILA(numeroDeCadeiras);
    
    
    private static void setListaDeClientes(int n){ 
        Barbearia.listaDeClientes = n;
    }
    
   
    /* 
        Ver Problema de DEMORA com o professor quando aumenta demais o número 
        ocorre um starvation e a fila não diminui mais.
        Os barbeiros ficam em repouso.
    */
   
    
    
    public static void main(String[] args) throws InterruptedException {
    
        setListaDeClientes(50);
        
        Semaphore  mutex = new Semaphore(1), 
                   barbeiro = new Semaphore(0),
                   sofa = new Semaphore(numeroDePessoasNoSofa),
                   clienteFilaSofa = new Semaphore(0),
                   clienteFilaCadeiras = new Semaphore(0),
                   pagamento = new Semaphore(0),
                   recebimento = new Semaphore(0);
        
    
        // for para os barbeiros.
        for(int i = 0; i < listaDeBarbeiros; i++){
            
            Barbeiro b = new Barbeiro(i, mutex, barbeiro, clienteFilaSofa,
                                clienteFilaCadeiras, pagamento, recebimento);
            
            b.start();
            Thread.sleep(TMIN);
   
        }
        
        // for para os clientes.
        for(int i = 0; i < listaDeClientes; i++){
            
            Cliente c = new Cliente(i, mutex, sofa, barbeiro, clienteFilaSofa,
                                clienteFilaCadeiras, pagamento, recebimento);

            Thread.sleep(TMIN + periodo.nextInt(INTERVALO));
            c.start();
            Thread.sleep(TMIN);
            
        }
        
    }
    
}
