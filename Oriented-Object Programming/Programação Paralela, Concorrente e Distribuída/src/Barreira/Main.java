
package Barreira;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main{

    
    public static int TMIN = 1500;
    public static int TMAX = 5000;
    public static int INTERVALO = TMAX - TMIN;
    private static Random periodo = new Random();
     
    public static int parte1, parte2, parte3, parte4;
   
    public static void setPartes(int t){
        
        int size = t*4;
        
        parte1 = 0;
        parte2 = parte1+ size/4;
        parte3 = parte2 +size/4;
        parte4 = parte3+size/4;
    
    }    
    
    public static Funcionario[] funcionario;
    
    public static int contador = 0;
    
    public static Semaphore mutex = new Semaphore(1);
    
    public static Semaphore barreira = new Semaphore(0);
    
    
    public static void main(String[] args) throws InterruptedException {
        
        
        Semaphore mutex1 = new Semaphore(1),
                  mutex2 = new Semaphore(1),
                  mutex3 = new Semaphore(1),
                  mutex4 = new Semaphore(1);
        
        
        int tamanhoLista = 20;
        
        funcionario = new Funcionario[tamanhoLista*4];
        
        setPartes(tamanhoLista);
        
        for(int i = 0; i < funcionario.length; i++){
            funcionario[i] = new Funcionario(i);
        }
        
        Thread1 thread1 = new Thread1(mutex1, mutex2, mutex3, mutex4);
        Thread2 thread2 = new Thread2(mutex1, mutex2, mutex3, mutex4);
        Thread3 thread3 = new Thread3(mutex1, mutex2, mutex3, mutex4);
        Thread4 thread4 = new Thread4(mutex1, mutex2, mutex3, mutex4);
        
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        
        mutex.acquire();
            
            contador += 1;
            
        mutex.release();
        
        if(contador == 4){ barreira.release();}
        
        barreira.acquire();
            
        barreira.release();
        
    }
    
    
}
