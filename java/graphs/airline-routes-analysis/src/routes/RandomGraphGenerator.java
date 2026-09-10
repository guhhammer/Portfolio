package routes;

import java.util.ArrayList;
import java.util.Random;
 
public class RandomGraphGenerator {

	private static int vertexCount, edgeCount, boundary = 100000; // vertices, edges and weight bound.
	private static boolean connected, directed;   // connected and directed flags.
	private static Graph randomGraph;  // graph.
	
	// sets the parameters.
	private static void configure(int k, int a, boolean c, boolean d){
		vertexCount = k;  edgeCount = a;  connected = c;  directed = d;
	}
	
	// sets the weight bound.
	public static void setBoundary(int b) { boundary = b; }
	
	// returns the graph.
	private static Graph getGraph() { return randomGraph; }

	// builds the graph.
	private static void build() {
		
		randomGraph = new Graph(vertexCount, directed);
		
		Random rd = new Random();
		
		if(connected) {
			
			ArrayList<Integer> remaining = new ArrayList<>();
			
			for(int i = 0; i < vertexCount; i++) { remaining.add(i); }
			
			int from = rd.nextInt(remaining.size()), to, index;
			remaining.remove(from);
			
			while(!remaining.isEmpty()) {
				index = rd.nextInt(remaining.size());
				to = remaining.get(index);
				remaining.remove(index);
				randomGraph.addEdge(from, to, rd.nextInt(boundary));
				from = to;
			}
			
			for(int i = vertexCount-1; i < edgeCount; i++) {
				
				randomGraph.addEdge(rd.nextInt(vertexCount), rd.nextInt(vertexCount), rd.nextInt(boundary));
				
			}
			
			if(!randomGraph.isConnected()) { build(); }
			
		}
		else {
			
			int remainingEdges = edgeCount;
			while(remainingEdges > 0) {
				
				randomGraph.addEdge(rd.nextInt(vertexCount), rd.nextInt(vertexCount), rd.nextInt(boundary));
				remainingEdges--;
			}
			
			if(edgeCount == 1) { return; }
			if(randomGraph.isConnected()) { build(); }
	
		}
		
	}
			
	// public entry point: validates the parameters and builds the graph.
	public static Graph make(int vertexCount, int edgeCount, boolean connected, boolean directed){
		
		if(vertexCount == 0 || edgeCount == 0) { 
			String s = (vertexCount == 0) ? "\n\nNote: the graph has 0 vertices,"
					+ " so it is trivially connected.\n\n" : "";
			System.out.print(s);
			return new Graph(vertexCount, directed);
		}
		
		if(vertexCount-1 > edgeCount) {  
		
			System.out.print("\n\nNote: fewer edges than vertices;"
						   + " the graph will be disconnected.\n\n");
			configure(vertexCount, edgeCount, false, directed);
			
		}
		else { configure(vertexCount, edgeCount, connected, directed); }
		
		if(!connected && edgeCount > vertexCount*2) {
			
			System.out.print("\n\nNote: too many edges for a disconnected graph "
					+ "(more than twice the vertex count).\n"
					+ "The graph starts with 0 edges.\n\n");
			
			edgeCount = (int)(vertexCount);
			
			return new Graph(vertexCount, directed);
			
		} 
		
		
		build(); 
		
		return getGraph();
		
	}
	
	
}
