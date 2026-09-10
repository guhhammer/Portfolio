
package mst;

import mst.SparseMatrix.*;

public class Graph{
    
    int size;
    Vertex[] vertices;
    SparseMatrix matrix;
    
    // vertex.
    public class Vertex{
        
        String name;
        int index;
        
        // setters.
        void setName(String n){ this.name = n;}
        void setIndex(int n){ this.index = n;}
        
        // getters.
        String getName(){ return name;}
        int getIndex(){ return index;}
        
    }
    
    // constructor.
    public Graph(int size){
        
        SparseMatrix aux = new SparseMatrix(size);
        
        this.size = size;
        this.matrix = aux;
        this.vertices = new Vertex[size];
       
        for(int i = 0; i < size; i++){vertices[i] = new Vertex();}  
        
        for(int i = 0; i < size; i++){vertices[i].setIndex(i);} 
        
        for(int i = 0; i < size; i++){vertices[i].setName(""+i);}
        
    }
   
    // adds an undirected edge between i and j.
    void addUndirectedEdge(int i, int j, int p){
        this.matrix.lists[i].insert(j, p);
        this.matrix.lists[j].insert(i, p);
    }
    
    // directed insertion, used by the MST algorithms.
    void addEdge(int i, int j, int p){
        this.matrix.lists[i].insert(j, p);
    }
    
    // removes the undirected edge between i and j.
    void removeUndirectedEdge(int i, int j){
        this.matrix.lists[i].remove(j);
        this.matrix.lists[j].remove(i);
    }
    
    // removes the edge from i to j.
    void removeEdge(int i, int j){
        this.matrix.lists[i].remove(j);
    }
    
    // prints the adjacency lists.
    void printEdges(){
        
        for(int  i = 0; i < this.size; i++){
            Node aux = this.matrix.lists[i].first;
            System.out.print("Vertex "+i+": ");
            while(aux != null){
                System.out.print(" {adjacent: "+aux.adjacent+
                                 ", value: "+aux.value+"} ");
                aux = aux.next;
            }
            System.out.println("");
        }
        
    }
    
    // prints the adjacency lists.
    void printUndirectedEdges(){
        
        Graph g = new Graph(this.size);
        
        for(int i = 0; i < this.size; i++){
            Node aux = this.matrix.lists[i].first;
            while(aux != null){
                g.addUndirectedEdge(i, aux.adjacent, aux.value);
                aux = aux.next;
            }
        }
         
        for(int  i = 0; i < this.size; i++){
            Node aux = g.matrix.lists[i].first;
            System.out.print("Vertex "+i+": ");
            while(aux != null){
                System.out.print(" {adjacent: "+aux.adjacent+
                                 ", value: "+aux.value+"} ");
                aux = aux.next;
            }
            System.out.println("");
        }
        
    }
    
    // returns whether the edge exists.
    boolean hasEdge(int i, int j){
        return this.matrix.lists[i].contains(j);
    }
    
    // sets the vertex label.
    void setLabel(int i, String v){vertices[i].setName(v);}
    
    // fills adj with the neighbours of i and returns how many there are.
    int neighbours(int i, int[] adj){
        
        int count = 0,index = 0; // Integer.MIN_VALUE marks an unused slot.
        for(int s = 0; s < adj.length; s++){ adj[s] = Integer.MIN_VALUE; }
        
        Node aux = this.matrix.lists[i].first;
        while(aux != null){ 
            adj[index++] = aux.adjacent; count++; aux = aux.next;
        }
        
        return count;
    }
    
    // returns the weight of the edge i -> j.
    int valueOf(int i, int j){return this.matrix.lists[i].valueOf(i, j);}
    
    // finds the index of vertex j.
    public int findVertex(int j){
        int v = -1;
        for(int i = 0; i < size; i++){if(i == j){ v = i; }}
        return v;
    }
    
}