
package aula05.poo;

import java.util.ArrayList;

import java.util.Scanner;

public class AulaO5POOTESTE {
    public static ArrayList<Double> ponto = new ArrayList<Double>();
    
    Scanner s = new Scanner(System.in);
    
    public static void main(String[] args){
        PONTO p1 = new PONTO(2,3);
        PONTO p2 = new PONTO(8,5);
        
        reta a = new reta(3,1);
        System.out.println(a.RETA(p1, p2));
    }
    
}
