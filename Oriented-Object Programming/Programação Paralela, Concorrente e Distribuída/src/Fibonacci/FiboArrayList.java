
package Fibonacci;

import java.util.ArrayList;

public class FiboArrayList {
    
    static int elem;
    public static ArrayList<Integer> fib = new ArrayList<>();
    static int second;
    static int resposta;
    
    public static void make(){
        
        fib.clear();
        
        if(elem == 0){ fib.add(0); }
        else if(elem == 1){ fib.add(1); }
        else{
            
            fib.add(0);
            fib.add(1);
            
            int aux = 1;
            while(aux != elem){
                
                fib.add(((Integer)fib.get(fib.size()-1)+(Integer)fib.get(fib.size()-2)));
                
                aux++;
            }
            
        }        
        
    }
    
    public static void setElem(int n){ elem = n; }
    public static Integer fib(){ return (Integer)fib.get(fib.size()-1);}
   
    public static Integer fib(int n){ 
        
        setElem(n); 
        make();
        
        return fib();
    
    }
    
    
    
}
