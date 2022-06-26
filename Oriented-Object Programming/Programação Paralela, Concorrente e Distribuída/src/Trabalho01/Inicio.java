
package Trabalho01;

import java.util.concurrent.Semaphore;

public class Inicio {
   
    
    // É a String que sai do Gerador para o Filtro(GF).
    public static String bufferGF = "";
    
    
    // É a String que sai do Filtro para o Analisador (FA).
    public static String bufferFA = "";
    
    
    // É o tempo de inatividades das Threads.
    public static int sleepTimeThread = 1000;
    
    
    public static void main(String[] args) {
        
           
        // Mutação Exclusiva -> Flag(Semáforo).
        Semaphore mutexG = new Semaphore(1); 
        
        Semaphore mutexA = new Semaphore(1);
        
        // Flag1 -> Bastão da vez inicial. Gerador o usa.
        Semaphore flag1 = new Semaphore(1);
        
        // Flag2 -> Bastão que sucede flag2. Filtro o usa.
        Semaphore flag2 = new Semaphore(0);
        
        // Flag3 -> Bastão que sucede flag3. Analisador o usa.
        Semaphore flag3 = new Semaphore(1);
        
        Semaphore flag4 = new Semaphore(0);
        
        
        // Objetos:
        Gerador g = new Gerador(mutexG, flag1, flag2);
        Filtro f = new Filtro(mutexG, mutexA, flag2, flag1, flag3, flag4);
        Analisador a = new Analisador(mutexA, flag4, flag3);
        
        
        // Inicialização do Programa:
        g.start();
        f.start();
        a.start();
        
    }
    
}
