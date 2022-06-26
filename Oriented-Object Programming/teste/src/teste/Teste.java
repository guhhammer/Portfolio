
package teste;

import java.util.Scanner;

public class Teste {
    
    static Scanner lk = new Scanner(System.in);
    
    
    
    public static int[] createVector(int n){
        
        System.out.println("Tamanho do vetor = "+n);
        System.out.println("Crie seu vetor: \n");
        int[] A = new int[n];
        
        for(int i = 0; i < A.length; i++){
            System.out.print("Digite o elemento["+i+"]:  ");
            A[i] = lk.nextInt(); 
        }
        for(int i = 0; i < A.length; i++){
            if(i == 0){ System.out.print("\nAntes:  A["+A.length+"] = { "+A[i]+", ");}
            else if(i < (A.length-1)){System.out.print(A[i]+", ");}
            else{System.out.print(A[i]+" } \n\n");}
        }
        
        return A;
        
    }
    
    public static void BubbleSort(int[] A){
      int n = A.length;
      int temp = 0;

      for(int i = 0; i < n; i++) {
         for(int j=1; j < (n-i); j++) {
            if(A[j-1] > A[j]) {
               temp = A[j-1];
               A[j-1] = A[j];
               A[j] = temp;
            }
         }
      }
      
      for(int i = 0; i < A.length; i++){
          if(i == 0){ 
                System.out.print("\n* Ordenação por meio de Bubble Sort: ");
                System.out.print("\nDepois: A["+A.length+"] = { "+A[i]+", ");
          }
          else if(i < (A.length-1)){System.out.print(A[i]+", ");}
          else{System.out.print(A[i]+" } \n\n");}
      }
    }
    
    public static void main(String[] args) {
        BubbleSort(createVector(10));
    }
    
}
