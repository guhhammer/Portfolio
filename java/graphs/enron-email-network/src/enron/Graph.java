
package enron;

public class Graph {
    
    // author: Gustavo Hammerschmidt.
    
    private int size; // number of vertices.
    Vertex[] vertices;  // vertices.
    SparseMatrix matrix;     // adjacency lists
    
    
    // vertex.
    public class Vertex{
        
        private String name; // email address.
        
        // setter.
        public void setName(String n){ this.name = n;}
        
        // getter.
        public String getName(){ return name;}
        
    }
    
    
    // constructor.
    public Graph(String[] names){
               
        this.size = names.length;
        this.matrix = new SparseMatrix(size);
        this.vertices = new Vertex[size];
        
        
        for(int i = 0; i < size; i++){vertices[i] = new Vertex();}
        
        // label each vertex.
        for(int i = 0; i < size; i++){vertices[i].setName(names[i]);}
        
    }
    
    // number of vertices.
    public int getSize(){ return this.size;}
   
    // index of the vertex with this email.
    public int findVertex(String email){
        int v = -1;
        for(int i = 0; i < size; i++){
            if(email.equals(vertices[i].getName())){  v = i; }
        }
        return v;
    }
    
    // email of vertex s.
    public String emailOf(int s){ return this.vertices[s].getName(); }
    
    // total edge weight.
    private int countAllEdges(){
        int counter = 0;
        for(int i = 0; i < this.getSize(); i++){
            counter += this.matrix.lists[i].countEdges();
        }                     
        return counter;
    }
    
    // number of edges.
    public int getEdgeCount(){ return this.countAllEdges();}
    
    
}
