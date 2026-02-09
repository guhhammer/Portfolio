
package notação_polonesa;

import java.util.Scanner;

public class Notação_Polonesa {
    
    static Scanner lk = new Scanner(System.in);
    
    public static void execute1(){
        System.out.println("\nPara float, double usar ','! "
                + "\nExemplo: 0,1; 8,5 !\n");
        
        //Exemplo a:
        
        System.out.println("\n ADIÇÃO:\n"
                + "Defina o tamanho do seu vetor: ");
        int tamA = lk.nextInt();
        double[] xA = new double[tamA];
        
        for(int i = 0; i < tamA; i++){
            System.out.println("Digite o elemento ["+i+"]: ");
            xA[i] = lk.nextDouble();
        }
        
        Expression e1 = new addition(xA);
        System.out.print("Exemplo a: "+
                         "\nExpressão: "+e1.toStringPolishNotation()+
                         "\nValor: "+e1.avaliar()+
                         "\n");
        // Exemplo fim.
        
        //Exemplo b:
        
        System.out.println("\n SUBTRAÇÃO:\n"
                + "Defina o tamanho do seu vetor: ");
        int tamB = lk.nextInt();
        double[] xB = new double[tamB];
        
        for(int i = 0; i < tamB; i++){
            System.out.println("Digite o elemento ["+i+"]: ");
            xB[i] = lk.nextDouble();
        }
        
        Expression e2 = new subtraction(xB);
        System.out.print("Exemplo a: "+
                         "\nExpressão: "+e2.toStringPolishNotation()+
                         "\nValor: "+e2.avaliar()+
                         "\n");
        // Exemplo fim.
       
        //Exemplo c:
        
        System.out.println("\n MULTIPLICAÇÃO:\n"
                + "Defina o tamanho do seu vetor: ");
        int tamC = lk.nextInt();
        double[] xC = new double[tamC];
        
        for(int i = 0; i < tamC; i++){
            System.out.println("Digite o elemento ["+i+"]: ");
            xC[i] = lk.nextDouble();
        }
        
        Expression e3 = new multiplication(xC);
        System.out.print("Exemplo a: "+
                         "\nExpressão: "+e3.toStringPolishNotation()+
                         "\nValor: "+e3.avaliar()+
                         "\n");
        // Exemplo fim.
        
        //Exemplo d:
        
        System.out.println("\n DIVISÃO:\n"
                + "Defina o tamanho do seu vetor: ");
        int tamD = lk.nextInt();
        double[] xD = new double[tamD];
        
        for(int i = 0; i < tamD; i++){
            System.out.println("Digite o elemento ["+i+"]: ");
            xD[i] = lk.nextDouble();
        }
        
        Expression e4 = new division(xD);
        System.out.print("Exemplo a: "+
                         "\nExpressão: "+e4.toStringPolishNotation()+
                         "\nValor: "+e4.avaliar()+
                         "\n");
        // Exemplo fim.
    }
    
    
    public static void main(String[] args) {
        
        execute1();
    }
    
}