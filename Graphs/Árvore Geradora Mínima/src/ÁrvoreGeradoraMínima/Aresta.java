
package ÁrvoreGeradoraMínima;

import java.util.*;

public class Aresta implements Comparator<Aresta>, Comparable<Aresta>{
        
    private int[] aresta = new int[3];
    
    // Comparador.
    public Aresta(){}
    
    // Construtor.
    public Aresta(int i, int j, int value){
        this.aresta[0] = i;
        this.aresta[1] = j;
        this.aresta[2] = value;
    }
    
    // Métodos get.
    public int getI(){ return this.aresta[0]; }
    public int getJ(){ return this.aresta[1]; }
    public int getValue(){ return this.aresta[2]; }
    
    /*
       Override da função de Comparator, usada para ordenar
       lista de arestas pelos seus vértices i.
    */
    @Override
    public int compareTo(Aresta a) {
        return (this.getI() <= a.getI()) ? this.getI() : a.getI();
    }
    
    
    /*
        Override da função de Comparable, usada para ordenar 
        lista de arestas pelo peso da adjacência.
    */
    @Override
    public int compare(Aresta a1, Aresta a2) {
        return a1.getValue() - a2.getValue();
    }
    
}