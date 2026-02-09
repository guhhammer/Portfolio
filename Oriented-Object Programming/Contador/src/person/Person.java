
package person;

import java.util.Scanner;


public class Person {
    
    static Scanner lk = new Scanner(System.in);
    
  
    
    
    public static pessoa cadastrar(){
        System.out.print("\nNome(String): ");
        String nome = lk.next();
        System.out.print("\nSobrenome(String): ");
        String sobrenome = lk.next();
        System.out.print("\nIdade(int): ");
        int idade = lk.nextInt();
        System.out.print("\nSalário(double): ");
        double salario = lk.nextDouble();

        pessoa cadastro = new pessoa(nome, sobrenome, idade, salario);       
        return cadastro;
    }
    
  
    
   
    
    public static void main(String[] args) {
        System.out.print("Quantos deseja cadastrar:");
        int comprimento = lk.nextInt();
        pessoa[] a = new pessoa[comprimento];
        for(int i = 0; i < comprimento; i++){
            a[i] = cadastrar();
        }
        System.out.println("\n\n    Cadastro Concluído!!!!\n");
        for(int i = 0; i < a.length; i++){
            System.out.println((i+1)+"ª pessoa:");
            a[i].imprimir(a[i]);
            System.out.println("");
        }
    }
    
}
