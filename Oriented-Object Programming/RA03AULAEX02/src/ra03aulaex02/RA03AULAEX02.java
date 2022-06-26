
package ra03aulaex02;

import java.util.Scanner;

public class RA03AULAEX02 {
  
    static Scanner lk = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("\nPara float, double usar ','! "
                + "\nExemplo: 0,1; 8,5 !\n");
        
        //Exemplo a: (2+5)
        Constante a1 = new Constante(2);
        Constante b1 = new Constante(5);
        Addition e1 = new Addition(a1.getValor(), b1.getValor());
        System.out.print("Exemplo a: "+
                         "\nExpressão: "+e1.toString()+
                         "\nValor: "+e1.avaliar()+
                         "\n");
        // Exemplo fim.
        
        System.out.println("\n \n");
        
        //Exemplo b: (A * (2 * 5))
        int i = 0;
        int k;
        double j;
        
        while(i == 0){ 
        System.out.print("Exemplo b: \nDefina a variável A: ");
        j = lk.nextDouble();
        Variavel v1 = new Variavel(j, "A");
        Constante a2 = new Constante(2);
        Constante b2 = new Constante(5);
        Expression p1 = new Multiplication(a2.getValor(), b2.getValor());
        Expression e2 = new Multiplication(v1.getValor(), p1.avaliar());
        System.out.println("\nExpressão (C/ var.): "+
                            e2.toStringMUL(v1.toString(), 
                            p1.toStringMUL(a2.toString(), b2.toString()))+
                           "\n \nExpressão: (S/ var.): "+
                            e2.toStringMUL(v1.toStringChanger(j),
                            p1.toStringMUL(a2.toString(), b2.toString()))+
                           "\nValor: "+e2.avaliar()+
                           "\n");
  
        System.out.println("Deseja continuar(s => 1 / n => 0): ");
        k = lk.nextInt();
        
        if(k == 0) i = 1;
        else if(k == 1) i = 0;
        else i = 1;
        // Exemplo fim.
        }
        
        System.out.println("\n \n");
        
        // Exemplo c: ((B/A)-(A*(2*5)))
        int p = 0;
        int q;
        double r,s;
        
        while(p == 0){ 
        System.out.print("Exemplo c: \nDefina a variável A: ");
        r = lk.nextDouble();
        if( r == 0){
            System.out.print("\nDivisão por zero. Redefinindo A para valor 1");
            r = 1;
            System.out.print("\nA = 1  !!!");
        }
        System.out.print("\nDefina a variável B: ");
        s = lk.nextDouble();
        Variavel v2 = new Variavel(r, "A");
        Variavel v3 = new Variavel(s, "B");
        Constante a3 = new Constante(2);
        Constante b3 = new Constante(5);
        Expression c1 = new Multiplication(a3.getValor(), b3.getValor());
        Expression c2 = new Multiplication(v2.getValor(), c1.avaliar());
        Expression c3 = new Division(v3.getValor(), v2.getValor());
        Expression e3 = new Subtraction(c3.avaliar(), c2.avaliar());
        
        System.out.println("\nExpressão (C/ var.): "+
            e3.toStringSUB(c3.toStringDIV(v3.toString(), v2.toString()),
            c2.toStringMUL(v2.toString(), 
            c1.toStringMUL(a3.toString(), b3.toString())))+
                           "\n \nExpressão: (S/ var.): "+
            e3.toStringSUB(c3.toStringDIV(v3.toStringChanger(s), 
            v2.toStringChanger(r)), c2.toStringMUL(v2.toStringChanger(r), 
            c1.toStringMUL(a3.toString(), b3.toString())))+
                           "\nValor: "+e3.avaliar()+
                           "\n");
  
        System.out.println("Deseja continuar(s => 1 / n => 0): ");
        q = lk.nextInt();
        
        if(q == 0) p = 1;
        else if(q == 1) p = 0;
        else q = 1;
        // Exemplo fim.
        
        }
        
    }    

}
