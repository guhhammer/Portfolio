
package sparsematrix;

import sparsematrix.SparseMatrix.*;

public class Graph{
    
    int size;
    Vertex[] vertices;
    SparseMatrix matrix;
    
    public class Vertex{
        
        String name;
        
        void setName(String n){ this.name = n;}
        
        String getName(){ return name;}
    }
    
    public Graph(int size){
        
        SparseMatrix aux = new SparseMatrix(size);
        
        this.size = size;
        this.matrix = aux;
        this.vertices = new Vertex[size];
       
        for(int i = 0; i < size; i++){vertices[i] = new Vertex();}   
    }
    
    
    void addEdge(int i, int j, int p){this.matrix.lists[i].insert(j, p);}
    
    void removeEdge(int i, int j){this.matrix.lists[i].remove(j);}
    
    void printEdges(){
        for(int  i = 0; i < this.size; i++){
            Node aux = this.matrix.lists[i].first;
            System.out.print(i+" row: ");
            while(aux != null){
                System.out.print(" {adjacent: "+aux.adjacent+
                                 ", value: "+aux.value+"} ");
                aux = aux.next;
            }
            System.out.println("");
        }
    }
    
    void setLabel(int i, String v){vertices[i].setName(v);}
  
    int neighbours(int i, int[] adj){
        
        int count = 0,index = 0; // Integer.MIN_VALUE marks an unused slot.
        for(int s = 0; s < adj.length; s++){ adj[s] = Integer.MIN_VALUE; }
        
        Node aux = this.matrix.lists[i].first;
        while(aux != null){ 
            adj[index++] = aux.adjacent; count++; aux = aux.next;
        }
        
        return count;
    }
    
    int valueOf(int i, int j){return this.matrix.lists[i].valueOf(i, j);}
    
    //team: Gustavo Hammerschmidt, Kalebe Rodrigues.
    
    public static void main(String[] args){

        /*
        Assignment: a directed, weighted, labelled graph on an adjacency structure.
      
            The graph:
        
                       4      --> x (label).
                     / | \
                    /  |  \
                   0---|---1
                   |   |   |    Note: there is also an
                   |   |   |           edge x -> 2, weight 4
                   2---|---3
                    \  |  /
                     \ | /
                       5      --> y (label).  
        
        Weights: 
            -     0 -> x : 3
            -     2 -> 0 : 6 
            -     x -> 2 : 4
            -     x -> y : 10
            -     2 -> 3 : 1
            -     y -> 2 : 3
            -     3 -> y : 1
            -     3 -> 1 : 7
            -     1 -> x : 8
        */
        
        Graph l = new Graph(6);      // the graph.
        
       
        l.addEdge(0, 4, 3);
        l.addEdge(2, 0, 6);   // edges and weights.
        l.addEdge(4, 2, 4);
        l.addEdge(4, 5, 10);
        l.addEdge(2, 3, 1);
        l.addEdge(5, 2, 3);
        l.addEdge(3, 5, 1);
        l.addEdge(3, 1, 7);
        l.addEdge(1, 4, 8);
        
        l.printEdges();    // print the adjacency lists.
        
        System.out.println("\n");
        l.addEdge(0, 0, 90);
        l.printEdges();
        System.out.println("");      // test remove().
        l.removeEdge(0, 0);
        l.printEdges();
                                             // weight of an edge. 
        System.out.print("\n\nWeight of (i: 2, j: 3): "+l.valueOf(2, 3));
        System.out.print("\nWeight of (i: 1, j: 4): "+l.valueOf(2, 3)+"\n");
        
        
        System.out.println("\n");
        l.setLabel(4, "x");
        l.setLabel(5, "y");    //  test the labels.
        System.out.println("Label of vertex "+4+": "+l.vertices[4].getName());
        System.out.println("Label of vertex "+5+": "+l.vertices[5].getName()+"\n\n");
       
        int[] adj = new int[6];        // neighbour array.
        System.out.println("Number of neighbours: "+l.neighbours(3, adj));
        System.out.println("");
        System.out.print("adj array:   ");
        for(int i = 0; i < adj.length; i++){
            if(adj[i] == Integer.MIN_VALUE){System.out.print(" INF");}
            else{ System.out.print(" "+adj[i]);}
        }
        System.out.println("\n");
        
    }
    
}
