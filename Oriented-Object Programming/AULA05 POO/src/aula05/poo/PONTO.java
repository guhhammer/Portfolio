/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula05.poo;

import java.util.Scanner;


/**
 *
 * @author Gustavo
 */
public class PONTO {
        Scanner s = new Scanner(System.in);
    
    
    private double x;
    private double y;
    protected double[] ponto = new double[2];
    
    
   /* public PONTO(double pX, double pY){
        this.x = pX;
        this.y = pY;
    }   //construtor de pontos
    */
    
    public PONTO(double pX, double pY){
        ponto[0] = pX;
        ponto[1] = pY;
    }
    
    public String toString(){
        return "Ponto{"+"x="+ponto[0]+", y="+ponto[1]+"}";
    }
    
    
}
