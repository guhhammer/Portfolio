
package BarbeariaFIFO;

import java.util.concurrent.Semaphore; 

public class FILA { 
    
    private Semaphore dado[];
    private static int primeiro = 0, ultimo = 0, MAX, tamanho = 0; 
    
    public FILA(int MAX){ this.MAX = MAX; this.dado = new Semaphore[this.MAX]; }
    
    public int max(){return this.MAX;}
    
    protected boolean vazia(){ return (tamanho == 0);}
    
    protected boolean cheia(){ return (tamanho == MAX-1);}
    
    protected Semaphore primeiro(){ return dado[this.primeiro];}
    
    protected Semaphore ultimo(){ return dado[this.ultimo];}
    
    protected void inserir(Semaphore c){
        if(!cheia()){
            dado[ultimo++] = c; tamanho++;
            if(ultimo == MAX-1){ ultimo = 0;}
        }
    }
    
    protected Semaphore remover(){
        Semaphore aux = null;
        if(!vazia()){
            aux = dado[primeiro]; dado[primeiro++] = null; tamanho--;
            if(primeiro == MAX-1){ primeiro = 0;}
        }
        return aux; 
    }

}
