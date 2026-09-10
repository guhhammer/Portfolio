


import java.util.Scanner;


public class PersonRegistry {
    
    static Scanner lk = new Scanner(System.in);
    
  
    
    
    public static Person register(){
        System.out.print("\nName (String): ");
        String nome = lk.next();
        System.out.print("\nLast name (String): ");
        String lastName = lk.next();
        System.out.print("\nAge (int): ");
        int age = lk.nextInt();
        System.out.print("\nSalary (double): ");
        double salary = lk.nextDouble();

        Person cadastro = new Person(nome, lastName, age, salary);       
        return cadastro;
    }
    
  
    
   
    
    public static void main(String[] args) {
        System.out.print("Quantos deseja register:");
        int length = lk.nextInt();
        Person[] a = new Person[length];
        for(int i = 0; i < length; i++){
            a[i] = register();
        }
        System.out.println("\n\n    Registration complete!!!!\n");
        for(int i = 0; i < a.length; i++){
            System.out.println((i+1)+"ª person:");
            a[i].printPerson(a[i]);
            System.out.println("");
        }
    }
    
}
