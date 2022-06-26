
package zetaEquation;

import static java.lang.Math.pow;

public class zetaEquation {
    

    public static void zeta(int a, int precision){
        
        double p1;
        float Soma = 0;

        for(int i = 1; i < precision; i++){
            try{
                p1 = Math.pow(i, a);
                Soma += 1/p1;
                System.out.println("p1: "+p1+"\nSoma: "+Soma+"\n\\\\\\");
                p1 = 0;
                Thread.sleep(10);
            }
            catch(InterruptedException ex){Thread.currentThread().interrupt();}
        }
            
        System.out.println("Zeta("+a+") approximate to: "+Soma);
        
    }
    
    public static void main(String[] args) {zeta(2, 1000);}
}
