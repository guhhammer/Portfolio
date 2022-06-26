
package Fibonacci;

public class Fibonacci {   
    
    public static int[] fib(int y){
        int[] A = new int[y];
        A[0] = 1;
        A[1] = 1;
        for(int i = 2; i < y; i++){
            A[i] = A[i-1] + A[i-2];
        }
        return A;
    }
    
    public static void print(int[] A){
        System.out.print("Fibonacci["+A.length+"] = {");
        int cont  = 0;
        for(int i = 0; i < A.length; i++){
            if(i == (A.length-1)){
                System.out.println(A[i]+"} .");
            }
            else{
                System.out.print(A[i]+", ");
                cont++;
                if(cont == 10){
                    System.out.print("\n            ");
                    cont = 0;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        int x = 20;
        print(fib(x));
        
    }
}
