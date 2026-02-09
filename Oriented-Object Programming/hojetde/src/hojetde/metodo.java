/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hojetde;

import java.util.Scanner;

/**
 *
 * @author Gustavo
 */
public class metodo {
    
    public static void main(String[] args){
        boolean choice;
        Scanner scan = new Scanner(System.in);
        empresa Alfa = new empresa("Alpha","21/09/18");
        do{
            System.out.print("\nNome: ");
            String nome = scan.next();
            System.out.print("\nSobrenome: ");
            String sobrenome = scan.next();
            System.out.print("\nData: ");
            String data = scan.next();
            
            Alfa.cadastra(new funcionario(nome,sobrenome,"",0,0.0f));
            System.out.println("Deseja cadastrar mais um funcionario? (true/false)");
            choice = scan.nextBoolean();
            
        }while(choice);
    }
    
}
