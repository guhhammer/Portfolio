
package aula.pkg01.poo;

import java.util.Scanner;


public class Aula01POO {
    public static void main(String[] args) {
        int x = 0;
          
       double nota1, nota2, nota3, nota4, media;
      
       Scanner s = new Scanner(System.in);
       System.out.println("Type note 1:"); 
       nota1 = s.nextDouble();
       
       System.out.println(" Type note 2:");
       nota2 = s.nextDouble();
       
       System.out.println("Type note 3:");
       nota3 = s.nextDouble();
       
       System.out.println("Type note 4:");
       nota4 = s.nextDouble();
        
       media = (nota1 + nota2 + nota3 + nota4)/4;
      
       if (media > 7.0) {
        System.out.println("Media final = " + media);
        System.out.print("\n");
        System.out.println("Aprovado.");
       }
       
       else if (media == 7.0){
       System.out.println("Media final = " + media);
        System.out.print("\n");
        System.out.println("Aprovado.");
       
       }
       
       else if (media < 7.0){   
        System.out.println("Media final = " + media);
        System.out.print("\n");
        System.out.println("Reprovado.");
               
               }
 
}           
      

    
}
