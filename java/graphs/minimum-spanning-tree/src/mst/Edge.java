
package mst;

import java.util.*;

public class Edge implements Comparator<Edge>, Comparable<Edge>{
        
    private int[] edge = new int[3];
    
    // comparator constructor.
    public Edge(){}
    
    // constructor.
    public Edge(int i, int j, int value){
        this.edge[0] = i;
        this.edge[1] = j;
        this.edge[2] = value;
    }
    
    // getters.
    public int getI(){ return this.edge[0]; }
    public int getJ(){ return this.edge[1]; }
    public int getValue(){ return this.edge[2]; }
    
    /*
       Comparable: orders edges by their first vertex.
    */
    @Override
    public int compareTo(Edge a) {
        return (this.getI() <= a.getI()) ? this.getI() : a.getI();
    }
    
    
    /*
        Comparator: orders edges by weight.
    */
    @Override
    public int compare(Edge a1, Edge a2) {
        return a1.getValue() - a2.getValue();
    }
    
}