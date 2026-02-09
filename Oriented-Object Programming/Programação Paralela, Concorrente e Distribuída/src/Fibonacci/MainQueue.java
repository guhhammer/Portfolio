
package Fibonacci;

public class MainQueue {
    
    
    
    
    
    public static void main(String[] args) {
        
        int n = 45;
        
        long f1 = System.currentTimeMillis();
        Integer fib = FiboArrayList.fib(n);
        long f1f = System.currentTimeMillis()-f1;
        
        System.out.println("Fibonacci("+n+"): "+fib+"| Tempo: "+f1f);
        
        
    }
    
    
    
    
}
