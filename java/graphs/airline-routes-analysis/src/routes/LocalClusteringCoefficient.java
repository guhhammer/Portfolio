package routes;

import java.util.ArrayList;

public class LocalClusteringCoefficient {

	
	
	private static Graph g;
	
	
	private static void setGraph(Graph gr) { g = gr;}
	
	
	private static float coefficient(int vertex) {
		
		ArrayList<Integer> neighbourhood = new ArrayList<>();
		
		SparseMatrix.Node aux = g.matrix.lists[vertex].first;
		
		while(aux != null) {
			if(!neighbourhood.contains(aux.adjacent)) { neighbourhood.add(aux.adjacent); }
			aux = aux.next;
		}
		
		int degree = neighbourhood.size();
		
		int count = 0;
		
		for(int i = 0; i < neighbourhood.size(); i++) {
			
			SparseMatrix.Node other = g.matrix.lists[neighbourhood.get(i)].first;
			
			while(other != null) {
				
				if(neighbourhood.contains(other.adjacent) && other.adjacent != vertex) { count++; }
				
				other = other.next;
				
			}
			
		}
		
		if(g.directed) { return (degree*(degree-1)*1.0f == 0.0f) ? 0.0f : count/(degree*(degree-1)*1.0f); }
		else { return ((degree*(degree-1))/2*1.0f == 0.0f) ? 0.0f : (count)/(degree*(degree-1)*1.0f); }
		
	}
	
	
	private static void printCoefficients() {
		
		
		System.out.println("\n\nLocal Clustering Coefficient:\n");
		
		for(int i = 0; i < g.size; i++) {
			
			System.out.println("Vertex "+i+":  "+coefficient(i));
			
		}
		
		System.out.println("\n\n");
		
	}

	
	public static void printLocalClustering(Graph g) {
		
		setGraph(g);
		
		printCoefficients();
		
	}
	
	
	private static float averageCoefficient() {
		
		float sum = 0.0f;
		for(int i = 0; i < g.size; i++) { sum += coefficient(i);}
		
		return (1.0f/g.size)*sum;
		
	}
	
	public static void printAverageClustering(Graph g) {
		
		setGraph(g);
		
		System.out.println("\n\nAverage clustering coefficient:  "+averageCoefficient()+"\n\n");
		
	}
	
	
	
	
	public static void main(String[] args) {
	    
        int vertices = 15; // graph size. 
        boolean directed = false;
        
        Graph start = new Graph(vertices, directed); // create a graph. 
        
        // edges.
        start.addEdge(0, 1, 2);
        start.addEdge(0, 2, 1);
        start.addEdge(1, 3, 3);
        start.addEdge(1, 4, 2);
        start.addEdge(2, 3, 1);
        start.addEdge(2, 5, 2);
        start.addEdge(3, 4, 4);
        start.addEdge(3, 5, 3);
        start.addEdge(4, 6, 1);
        start.addEdge(5, 6, 3);
        start.addEdge(6, 7, 2);
        start.addEdge(6, 8, 2);
        start.addEdge(7, 8, 1);
        
        start.addEdge(9, 10, 5);
        start.addEdge(10, 11, 1);
        start.addEdge(9, 12, 3);
        start.addEdge(10, 12, 2);
        
        start.addEdge(13, 14, 5);
		printLocalClustering(start);
     
		
		printAverageClustering(start);
		
	}
	
	
}
