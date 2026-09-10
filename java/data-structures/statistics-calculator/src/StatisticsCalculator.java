


import java.util.Scanner;

public class StatisticsCalculator {
    
    static Scanner lk = new Scanner(System.in);
    
    public static void mean(double[] A){
        double soma = 0;
        for(int i = 0; i < A.length; i++){
            soma = soma + A[i];
        }
        
        System.out.println("Arithmetic mean of the array: "
                                                      +(soma/A.length)+"\n\n");
    }
    
    
    public static void mode(double[] A){
    
        int[] counter = new int[A.length];
        
        // loop: occurrences:
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A.length; j++){
                if(A[i] == A[j]){
                    counter[i] = counter[i] + 1;
                }
            }
        }//
        
        
        // loop: highest occurrence count:
        int maiorvalor = counter[0];
        for(int i = 0; i < counter.length; i++){
            if(maiorvalor < counter[i]){
                maiorvalor = counter[i];
            }
        }//
        
        // loop indice 
        int indice = 0;
        for(int i = 0; i < counter.length; i++){
            if(maiorvalor == counter[i]){
                indice = i;
                break;
            }
        }//
        
        System.out.println("\n");
        System.out.println("This is the array: ");
        System.out.print("Vetor["+A.length+"] = { ");
        for(int i =0; i < A.length; i++){
            if(i == (A.length-1)){
                System.out.print(A[i]+"} \n");
                System.out.println("");
                break;
            }
            else{
                System.out.print(A[i]+", ");
            }
        }
        
        System.out.println("Occurrence (repetition) array: ");
        System.out.print("Occurrences["+A.length+"] = { ");
        for(int i = 0; i < A.length; i++){
            if(i == (A.length-1)){
                System.out.print(A[i]+"} \n");
                System.out.println("");
                break;
            }
            else{
                System.out.print(counter[i]+", ");
            }
        }
        
        
        System.out.println("Now, the occurrence list: ");
        System.out.println("Vetor[]          ||         Occurrences: ");
        for(int i = 0; i < A.length; i++){
            System.out.println("Element VetorRead["+i+"] = "+A[i]+
                    " repeats "+(counter[i]-1)+"times. ");
        }
        System.out.print("\nFim"+"\n");
        
        System.out.println("Most frequent element of A: "
                                                         +A[indice]+"\n");
        
        
    }
    
    
    
    public static void main(String[] args) {
        
        
        int Comprimento = 10;
        double[] vetor = new double[Comprimento];
        for(int i = 0; i < vetor.length; i++){
            System.out.print("Enter the value of vetor["+i+"]:  ");
            vetor[i] = lk.nextInt();
            System.out.println("");
        }
        
        mean(vetor);
        System.out.println("\n");
        mode(vetor);
        
    }
    
}
