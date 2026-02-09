
package Barreira_Reusavel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
    
    public static int quantidadeDeNumeros = 1000000; // Mude para 1 milhão.
    public static int limite = quantidadeDeNumeros*10;
    public static int zeitWarten = 4000;
    
    public static int contador = 0;
    public static int numeroTrabalhadoras = 4;
    
    public static Queue<String> filaDeArquivos = new LinkedList<>();
    
    public static void main(String[] args) throws IOException {
        
        
        Semaphore barreiraEntrada = new Semaphore(0),
                  barreiraSaida = new Semaphore(1),
                  mutex = new Semaphore(1),
                  mutexConflito = new Semaphore(1),
                  thread1 = new Semaphore(0),
                  thread2 = new Semaphore(0),
                  thread3 = new Semaphore(0),
                  thread4 = new Semaphore(0);
        
        
        Trabalhadora t1 = new Trabalhadora(1,"Thread1", mutex, mutexConflito,
                                    thread1, barreiraEntrada, barreiraSaida);
        
        Trabalhadora t2 = new Trabalhadora(2,"Thread2", mutex, mutexConflito,
                                    thread2, barreiraEntrada, barreiraSaida);
        
        Trabalhadora t3 = new Trabalhadora(3,"Thread3", mutex, mutexConflito, 
                                    thread3, barreiraEntrada, barreiraSaida);
        
        Trabalhadora t4 = new Trabalhadora(4,"Thread4", mutex, mutexConflito,
                                    thread4, barreiraEntrada, barreiraSaida);
        
        Combinadora c = new Combinadora(thread1, thread2, thread3, thread4);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        c.start();
        
    }
    
}
