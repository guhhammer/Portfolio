
package routes;

public class EulerianGraph {
	
	
	private static int oddDegreeCount; // odd-degree vertex count.
	 
	private static Graph g; // graph.
	
	// resets the count.
	private static void resetCount() { oddDegreeCount = 0; } 
	
	// sets the graph.
	private static void setGraph(Graph graph) { g = graph; }
	 
	// counts the vertices of odd degree.
	private static void countOddDegrees() {
			
		int hold;
		for(int i = 0; i < g.size; i++) {
			hold = g.matrix.lists[i].count();
			if(hold % 2 != 0) { oddDegreeCount++; }
		}
		
	}
	
	// returns the count.
	private static int oddDegreeResult(){ return (int) Math.ceil(oddDegreeCount/2); }
	
	// runs the run in order.
	private static int run(Graph g) {
		
		resetCount();
		
		setGraph(g);
		
		countOddDegrees(); 
		
		return oddDegreeResult();
		
	}
	
	// public entry point.
	public static boolean isEulerian(Graph g) { 
		
		int v = run(g); 
		
		if(g.directed) { 
    		System.out.print("\n\nNote: the graph is directed; the Eulerian check only applies to undirected graphs (returns false).\n\n");
    		return false;
    	}
		
		return (v == 0) ? true : ((v == 2)); 
	
	}
	
}
