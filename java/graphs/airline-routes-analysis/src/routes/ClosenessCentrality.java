package routes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ClosenessCentrality {

	private static Graph g; // graph.
	
	private static ArrayList<Integer> frontier = new ArrayList<>(); // reachable vertices.
	
	private static Queue<Integer> p = new LinkedList<Integer>(); // builds the reachable set.
	
	// breadth-first search of the reachable vertices (directed graphs only).
	private static void search() {
		
		if(p.peek() == null) {  return; }
		
		if(!frontier.contains(p.peek())) {
			
			frontier.add(p.peek());
			
			SparseMatrix.Node aux = g.matrix.lists[p.peek()].first;
			while(aux != null) {  p.offer(aux.adjacent);  aux = aux.next;  }
			
		}
		
		p.poll();
		
		if(p.peek() != null) { search();}
		
	}
	
	// whether end is reachable from start.
	private static boolean isReachable(int start, int end) {
		
		frontier.clear();  p.clear();
		
		p.offer(start);
		
		search();
		
		for(Integer i : frontier) { if(i == end) { return true; } }
		
		return false;
		
	}
	
	// sets the graph.
	private static void setGraph(Graph gr) { g = gr; } 
	
	// closeness for an undirected graph.
	private static float closenessOf(int vertex) {
		
		int[] component = null; // component of the vertex.
		boolean flag = false;
		for(Object x_ : g.getComponents()) { 
		   component = (int[]) x_;
		   for(int i = 0; i < component.length; i++) { if(component[i] == vertex) { flag = true; }  if(flag) { break; } }
		   if(flag) { break; }
		}
	
		float sum = 0.0f; // sum of the distances from the vertex to its component. 
		for(int i = 0; i < component.length; i++) { 
			if(component[i] != vertex) { sum += Dijkstra.distance(g, vertex, component[i])*1.0f; }
		}
		
		return (sum == 0.0f) ? 0.0f : (float) Math.pow(sum, -1);

	}
	
	// closeness for a directed graph.
	private static float closenessOfDirected(int vertex) {
		
		int[] component = null; // component of the vertex.
		boolean flag = false;
		for(Object x_ : g.getComponents()) { 
		   component = (int[]) x_;
		   for(int i = 0; i < component.length; i++) { if(component[i] == vertex) { flag = true; }  if(flag) { break; } }
		   if(flag) { break; }
		}
		
		float sum = 0.0f; // sum of the distances from the vertex to its component. 
		for(int i = 0; i < component.length; i++) { 
			if(component[i] != vertex && isReachable(vertex, component[i])) {
				sum += Dijkstra.distance(g, vertex, component[i])*1.0f;
			}  // only reachable vertices count.
		}
		
		return (sum == 0.0f) ? 0.0f : (float) Math.pow(sum, -1);

	}
	
	// prints the closeness of every vertex.
	private static void printAll(boolean directed) {
		
		System.out.println("\n\nCloseness centrality:\n");
		for(int i = 0; i < g.size; i++) {
			System.out.print(String.format("closeness of vertex %d:   %f\n", i,
							(directed) ? closenessOfDirected(i) : closenessOf(i)));
		}
		System.out.println("\n\n");
	}
	
	// runs the run in order.
	private static void run(Graph g) {
		
		setGraph(g);
		
		printAll(g.directed);
		
	}
	
	// public entry point.
	public static void printCloseness(Graph g) { run(g); }
		
}
