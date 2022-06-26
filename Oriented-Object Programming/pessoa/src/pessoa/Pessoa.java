
package pessoa;

import java.util.ArrayList;
import java.util.Scanner;

public class Pessoa {
    
    static Scanner lk = new Scanner(System.in);
    
    ArrayList<person> listaDePessoas;
   
    public Pessoa(){
        this.listaDePessoas = new ArrayList();
    }
    
    public boolean cadastra(person p){return this.listaDePessoas.add(p);}
    
    
    
    
    public static void main(String[] args) {
        
        person so = new person("joao", "sed", 7, 2000);
        
        System.out.println(so);
        
        
        
        boolean choice;
       
        do{
            System.out.print("\nNome: ");
            String nome = lk.next();
            System.out.print("\nSobrenome: ");
            String sobrenome = lk.next();
            System.out.print("\nIdade: ");
            String idade = lk.next();
            System.out.println("Salário: ");
            String salario = lk.next();
            
            //person.cadastra(new person(nome,sobrenome,idade,salario));
            System.out.println("Deseja cadastrar mais um funcionario? (true/false)");
            choice = lk.nextBoolean();
            
        }while(choice);
    }
    
}
