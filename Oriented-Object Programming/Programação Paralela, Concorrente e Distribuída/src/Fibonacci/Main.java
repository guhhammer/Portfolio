
package Fibonacci;

import java.util.concurrent.Semaphore;
import static Fibonacci.FibSequencial.Fib;

public class Main {
    
    
    
    
    public static void main(String[] args) throws InterruptedException {
        
        int n = 45;
        
        long t1 = System.currentTimeMillis();
        int f1 = Fib(n);
        long t1f = System.currentTimeMillis() - t1;
        System.out.println("Fibonacci("+n+"): "+f1+"| Tempo: "+t1f);
            
        
        intGuardar i = new intGuardar();
        
        Semaphore s = new Semaphore(0);
        FibParalelo p = new FibParalelo(n, 0, s, i);
        
        long t2 = System.currentTimeMillis();
        p.start();
        s.acquire();
        long t2f = System.currentTimeMillis() - t2;
        System.out.println("Fibonacci("+n+"): "+i.print()+"| Tempo: "+t2f);
        
    }
    
    
    
}
