
package Fibonacci;

import java.util.concurrent.Semaphore;
import static Fibonacci.FibSequencial.Fib;

public class FibParalelo extends Thread{
    
    
    int n, nivel;
    intGuardar valor;
    Semaphore concluido;
    
    
    public FibParalelo(int n, int nivel, Semaphore c, intGuardar i){
        this.n = n; this.nivel = nivel; this.concluido = c; this.valor = i;
    }
    
    
    public void calcular(){
        
        if(n == 0){
            valor.valor = 0;
            return;
        }
        
        if(n == 1){
            valor.valor = 1;
            return;
        }
        
        if(Math.pow(2,nivel) >= Runtime.getRuntime().availableProcessors()){
            int f = Fib(n);
            valor.valor = f;
            return;
        }
        
        Semaphore s = new Semaphore(-1);
        
        
        intGuardar a = new intGuardar();
        intGuardar b = new intGuardar();
        
        FibParalelo l = new FibParalelo(n-1, nivel+1, s, a);
        FibParalelo r = new FibParalelo(n-2, nivel+1, s, b);
        
        l.start(); r.start();
        try{ s.acquire(); }catch(Exception e){}
             
       
        valor.valor = a.valor + b.valor;
        
        
    }
    
    
    
    public void run(){
        
        calcular();
        
        concluido.release();
    
    }
    
    
    
}
