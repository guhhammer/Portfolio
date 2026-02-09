/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula05.poo;

/**
 *
 * @author Gustavo
 */
public class reta extends PONTO{

    public reta(double pX, double pY) {
        super(pX, pY);
    }
    
    public float[] RETA(PONTO p1, PONTO p2){
        float a, b;
        float[] reta = new float[2];
        a = ((float)p1.ponto[1]-(float)p2.ponto[1])/((float)p1.ponto[0]-(float)p2.ponto[0]);
        b = ((((float)p1.ponto[1]+(float)p2.ponto[1])-(a*((float)p1.ponto[0]+(float)p2.ponto[0])))/2); 
        reta[0] = a;
        reta[1] = b;
        return reta;
    }
}
