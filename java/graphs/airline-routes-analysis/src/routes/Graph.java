package routes;

import routes.SparseMatrix.*;
import java.util.List;

public class Graph{
    
    int size;
    Vertex[] vertices;
    SparseMatrix matrix;
    boolean directed;
    
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
    public Graph(int size, boolean directed){
        
        SparseMatrix aux = new SparseMatrix(size);
        
        this.size = size;
        this.matrix = aux;
        this.vertices = new Vertex[size];
        this.directed = directed;
       
        for(int i = 0; i < size; i++){vertices[i] = new Vertex();}  
        
        for(int i = 0; i < size; i++){vertices[i].setIndex(i);} 
        
        for(int i = 0; i < size; i++){vertices[i].setName(""+i);}
        
    }
    
    // constructor.
    public Graph() {   }
   
    // constructor.
    public Graph(boolean directed) {  this.directed = directed;  }
    
    // adds an edge between i and j.
    void addEdge(int i, int j, int p){
    	if(directed) { this.matrix.lists[i].insert(j, p); }
    	else {
    		this.matrix.lists[i].insert(j, p);
            this.matrix.lists[j].insert(i, p); 
    	}
    } 
    
    // removes the edge between i and j.
    void removeEdge(int i, int j){
    	if(directed) {  this.matrix.lists[i].remove(j); }
    	else {
    		this.matrix.lists[i].remove(j);
            this.matrix.lists[j].remove(i);
    	}
    }
    
    // prints the adjacency lists.
    void printEdges(){
        
    	if(this.edgeCount() != 0) {
	       
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
    
    }
    
    // returns whether the edge exists.
    boolean hasEdge(int i, int j){ return this.matrix.lists[i].contains(j); }
    
    // sets the vertex label.
    void setLabel(int i, String v){vertices[i].setName(v);}
    
    // fills adj with the neighbours of i and returns how many.
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
    int findVertex(String j){
        int v = -1;
        for(int i = 0; i < size; i++){if(j.equals(this.vertices[i].getName())){ v = i; }}
        return v;
    }
    
    // finds the index of vertex j.
    int findVertex(int j){
        int v = -1;
        for(int i = 0; i < size; i++){if(i == j){ v = i; }}
        return v;
    }
    
    // returns the number of edges.
    int edgeCount() {
    	
    	int aux = 0;
    	for(int i = 0; i < this.size; i++) { aux += this.matrix.lists[i].count(); }
    	return aux;
    	
    }
    
    // returns the number of vertices.
    int vertexCount() { return this.size; }
    
    // returns the components.
    @SuppressWarnings("rawtypes")
	List getComponents(){ return Components.componentsOf(this); }
    
    // returns the number of components.
    int componentCount(){ return Components.componentsOf(this).size(); }
    
    // prints only the components.
    void printOnlyComponents(){
        if(this.edgeCount() != 0) {
        	Components.printOnlyComponents(Components.componentsOf(this));
        }
    }
    
    // prints the components and their count.
    void printComponents(){ 
    	if(this.edgeCount() != 0) {
    		Components.printComponents(Components.componentsOf(this));
    	}
    }   
    
    // returns whether the graph is connected.
    boolean isConnected(){  return (this.componentCount() < 2); }
    
    // returns whether the graph is Eulerian.
    boolean isEulerian() {  return EulerianGraph.isEulerian(this);   }
    
    // returns whether the graph has a cycle.
    boolean hasCycle() { 
    	if(directed) {	return Cycles.hasCycleDirected(this); }
    	else { return Cycles.hasCycleUndirected(this); }
    }
    
    // random graph with a weight bound.
    Graph random(int edges, boolean connected, int boundary) {
    	RandomGraphGenerator.setBoundary(boundary);
    	return RandomGraphGenerator.make(this.size, edges, connected, this.directed);
    }
    
    // random graph.
    Graph random(int edges, boolean connected) {
    	
    	return RandomGraphGenerator.make(this.size, edges, connected, this.directed);
    }
    
    // saves the graph as a .pajek file.
    void writePajek(String name) {
    	
    	try { PajekWriter.writePajek(this, name, directed); }
    	catch(Exception e) { e.getSuppressed(); }
    	
    }
    
    // loads a graph from a .pajek file.
    Graph readPajek(String name) {	  return PajekReader.read(name);  }
    
    // builds the airport graph from routes.csv (last question).
    Graph airlineRoutes() { return RoutesDatabase.load("data/routes.csv", this.directed); }
    
    // prints the airport graph.
    void printAirportEdges() {
    	
    	if(this.edgeCount() != 0) {
 	       
    		for(int  i = 0; i < this.size; i++){
	            Node aux = this.matrix.lists[i].first;
	            System.out.print("Airport "+this.vertices[i].getName()+": ");
	            while(aux != null){
	                System.out.print(" {adjacent airport: "+this.vertices[aux.adjacent].getName()+
	                                 ", value: "+aux.value+"} ");
	                aux = aux.next;
	            }
	            System.out.println("");
	        }
    
    	}
    	
    }
   
    // prints the betweenness centrality of every vertex.
    void betweennessCentrality() { BetweennessCentrality.printBetweenness(this, false); }
    
    // prints the betweenness centrality of every vertex.
    void betweennessCentrality(boolean showStacks) {
    	BetweennessCentrality.printBetweenness(this, showStacks);  
    }
    	
    // prints the closeness centrality of every vertex.
    void closenessCentrality() { ClosenessCentrality.printCloseness(this); }
    
    // prints the air bridges of at most `size` hops.
    void airBridges(String c, String d, int size, int count) {
    	AirBridges.printAirBridges(this, c, d, size, count, false);
    }
    
    // prints the air bridges of at most `size` hops and shows the search.
    void airBridges(String c, String d, int size, int count, boolean s) {
    	AirBridges.printAirBridges(this, c, d, size, count, s);
    }
    
    // prints the air bridges of at most `size` hops through `connections`.
    void airBridgesWithConnections(String c, String[] connections, String d, int size, int count) {
    	AirBridges.printAirBridgesWithConnections(this, c, connections, d, size, count, false);
    }
    
    // prints the air bridges of at most `size` hops through `connections` and shows the search.
    void airBridgesWithConnections(String c, String[] connections, String d, int size, int count, boolean s) {
    	AirBridges.printAirBridgesWithConnections(this, c, connections, d, size, count, s);
    }

}
