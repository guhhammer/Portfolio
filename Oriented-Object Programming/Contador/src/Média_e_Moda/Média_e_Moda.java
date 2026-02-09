/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Média_e_Moda;

import java.util.Scanner;

/**
 *
 * @author Gustavo
 */
public class Média_e_Moda{
    
    static Scanner lk = new Scanner(System.in);
    
    public static void mediaA(double[] A){
        double soma = 0;
        for(int i = 0; i < A.length; i++){ soma = soma + A[i]; }     
        System.out.println("Essa é a média aritmética do vetor: "
                                          +(soma/A.length)+"\n\n");
    }
    
    
    public static void moda(double[] A){
    
        int[] counter = new int[A.length];
        
        //loop ocorrências:
        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A.length; j++){
                if(A[i] == A[j]){ counter[i]++; }
            }
        }//
        
        // loop maior n. ocorrências:
        int maiorvalor = counter[0];
        for(int i = 0; i < counter.length; i++){
            if(maiorvalor < counter[i]){ maiorvalor = counter[i]; }
        }//
        
        // loop indice 
        int indice = 0;
        for(int i = 0; i < counter.length; i++){
            if(maiorvalor == counter[i]){ indice = i; break;
            }
        }//
        
        System.out.println("\n");
        System.out.println("Esse é o vetor: ");
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
        
        System.out.println("Esse é o vetor de ocorrências (repetições): ");
        System.out.print("Ocorrências["+A.length+"] = { ");
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
        
        
        System.out.println("Agora, a lista de ocorrências: ");
        System.out.println("Vetor[]          ||         Ocorrências: ");
        for(int i = 0; i < A.length; i++){
            System.out.println("Elemento VetorRead["+i+"] = "+A[i]+
                    " repete-se "+(counter[i]-1)+" vezes. ");
        }
        System.out.print("\nFim"+"\n");
        
        System.out.println("Esse é o elemento de maior ocorrência em A: "
                                                         +A[indice]+"\n");
        
        
    }
    
    
    
    public static void main(String[] args) {
        
        
        int Comprimento = 10;
        double[] vetor = new double[Comprimento];
        for(int i = 0; i < vetor.length; i++){
            System.out.print("Digite o valor de vetor["+i+"]:  ");
            vetor[i] = lk.nextInt();
            System.out.println("");
        }
        
        mediaA(vetor);
        System.out.println("\n");
        moda(vetor);
        
    }
    
}
