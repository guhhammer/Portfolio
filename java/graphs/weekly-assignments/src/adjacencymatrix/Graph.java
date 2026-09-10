
package adjacencymatrix;

import java.util.Collections;

public class Graph {
   
    int size;
    int[][] matrix;
    Vertex[] vertices;
    
    
    
    public class Vertex{
        
        String name;
        
        void setName(String n){ this.name = n;}
        
        String getName(){ return name;}
        
    }
    
    public static int[][] adjacencyMatrix(int t){ return new int[t][t];}
    
    public Graph(int size){
        
        this.matrix = adjacencyMatrix(size);
        this.size = size;
        this.vertices = new Vertex[size];
        
        for(int i = 0; i < size; i++){
            this.vertices[i] = new Vertex();
        }
        
    }
    
    
    
    void addEdge(int i, int j, int p){ this.matrix[i][j] = p;}
    
    void removeEdge(int i, int j){ this.matrix[i][j] = 0; }
    
    void printMatrix(){
        
        System.out.print("\n Adjacency matrix:\n\n* |");
        for(int i = 0; i < this.size; i++){System.out.print(" "+i);}
        System.out.println("\n"+
                String.join(" ", Collections.nCopies(this.size+2,"-")));
        
        for(int i = 0; i < this.size; i++){
            System.out.print(i+" |");
            for(int j = 0; j < this.size; j++){
                System.out.print(" "+this.matrix[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");
    }
    
    void printEdges(){
        System.out.print("\nEdges:\n\nFormat: {(index i), (index j), "
                         +"(weight p)}\n\n{  \n");
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                if(this.matrix[i][j] != 0){
                    System.out.print(String.format("{%d, %d, %d}",
                                        i, j, this.matrix[i][j])+", ");
                }
            }
            System.out.println("");
        }
        System.out.print("} \n\n");
    }
    
    void setLabel(int i, String v){ vertices[i].setName(v); }
    
    int neighbours( int i, int[] adj){
        
        int count = 0,index = 0;
        for(int s = 0; s < adj.length; s++){ adj[s] = -1; }
        
        for(int x = 0; x < this.size; x++){
            if(this.matrix[i][x] != 0){  count += 1;  adj[index++] = x; }
        }
        
        return count;
    }
    
    
    static void printNeighbours(int[] adj){
        int i = 0;
        System.out.print("Adj = { ");
        while(adj[i] != -1){ System.out.print(adj[i++]+", ");}
        System.out.print("} \n\n");    
    }
    
    // team: Gustavo Hammerschmidt, Kalebe Szlachta.
    
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
        
        Graph graph = new Graph(6);   // Graph.
        
        graph.addEdge(0, 4, 3);
        graph.addEdge(2, 0, 6);   // edges and weights.
        graph.addEdge(4, 2, 4);
        graph.addEdge(4, 5, 10);
        graph.addEdge(2, 3, 1);
        graph.addEdge(5, 2, 3);
        graph.addEdge(3, 5, 1);
        graph.addEdge(3, 1, 7);
        graph.addEdge(1, 4, 8);
        
    
        graph.addEdge(0,0, 9);   // to be removed ...    
        graph.printMatrix();
        graph.removeEdge(0, 0);
        
        graph.printMatrix();
        graph.printEdges();
        
        
        graph.setLabel(4, "x");
        graph.setLabel(5, "y");
        System.out.println("Label of vertex "+4+": "+graph.vertices[4].getName());
        System.out.println("Label of vertex "+5+": "+graph.vertices[5].getName()+"\n\n");
        
       
        
        int v = 2, l2[] = new int[graph.size], total = graph.neighbours(v,l2);
        System.out.println("Neighbours of row "+v+": "+total);
        printNeighbours(l2);
   
    }
    
}
