
package ra03aula;

import java.util.ArrayList;

public class RA03AULA {
   
    public static void main(String[] args) {
        
        /*Vendedor v1 = new Vendedor(150,10,"Daisy");
        System.out.println("Salario: "+v1.calcularSalario(5));*/
        
        Funcionario f1 = new Funcionario(150, 10, "João");
        Funcionario f2 = new Funcionario(140, 12, "Ded");
        Funcionario f3 = new Funcionario(90, 5, "Gustavo");
        Funcionario v1 = new Vendedor(200, 15, "velho");
        Funcionario v2 = new Vendedor(200, 7, "Naosei");
        Funcionario g1 = new Gerente(150, 10, "EUEUUE");
        ArrayList<Funcionario> bd = new ArrayList();
        for(Funcionario x: bd){
            System.out.println("Salario: "+x.calcularSalario());}
    }
    
}






