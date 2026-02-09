package aula04.poo;

import java.util.Scanner;

public class PONTO{
    
    Scanner ds = new Scanner(System.in);
    
    
    private static double x , y;
    
    public PONTO(double a, double b){    //construtor
        x = a;
        y = b;
    }
    
    public PONTO(){
        x = 0.0;
        y = 0.0;
    }
    
    
    
    public static void ValEX(){
        
    System.out.print("Ponto ("+x+", "+y+")");
    
    }




}