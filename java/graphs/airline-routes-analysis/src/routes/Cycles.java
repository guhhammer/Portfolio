package routes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Cycles{

	private static Graph g;  // graph.
	
	private static boolean hasCycle = false; // cycle flag.
 
	private static ArrayList<Integer> path = new ArrayList<>(); // visited vertices.
	
	private static Queue<Integer> children = new LinkedList<Integer>(); // children of a vertex.
	
	// sets the graph.
	private static void setGraph(Graph gr) { g = gr; } 
	
	// resets the flag.
	private static void resetCycle() { hasCycle = false; }
	
	// returns the flag.
	private static boolean cycleFound() { return hasCycle; }

	// checks for a self-loop.
	private static void checkSelfLoop(int i) {
		
		SparseMatrix.Node aux = g.matrix.lists[i].first;
		while(aux != null) {
			
			if(aux.adjacent == i) { hasCycle = true; return; }
			
			aux = aux.next;
			
		}
		
	}
	
	// cycle search for a directed graph.
	private static void searchDirected(int start) {
		
		if(children.peek() == start && path.size() != 0) {  hasCycle = true;  return; }
		
		if(!path.contains(children.peek())) {
			
			path.add(children.peek());
			
			SparseMatrix.Node aux = g.matrix.lists[g.findVertex(children.peek())].first;
			
			while(aux != null) { children.offer(aux.adjacent); aux = aux.next; }
		
		}
		
		children.poll();
		
		if(children.peek() != null) { searchDirected(start); }
		
	}
	
	// runs the directed search from every vertex.
	private static boolean runDirected(Graph g){
		
		setGraph(g);
		
		resetCycle();
		
		for(int i = 0; i < g.size; i++) {
			
			checkSelfLoop(i);
			
			if(cycleFound()) { break; }
			
			children.offer(i);
			
			searchDirected(i);
			
			children.clear();
			
			path.clear();
			
		}
		
		return cycleFound();
		
	}
	
	// public entry point.
	public static boolean hasCycleDirected(Graph g) { return runDirected(g); }
	
	
	private static Queue<Integer[]> childPairs = new LinkedList<Integer[]>(); // children of a vertex.
		
	// cycle search for an undirected graph.
	private static void searchUndirected(int start) {
		
		if(childPairs.peek()[1] == start && path.size() != 0) { hasCycle = true; return; }
					
		path.add(childPairs.peek()[1]);
		
		SparseMatrix.Node aux = g.matrix.lists[g.findVertex(childPairs.peek()[1])].first;
		
		while(aux != null) { 
							
			if(aux.adjacent != childPairs.peek()[0]) { 
				childPairs.offer(new Integer[] { childPairs.peek()[1], aux.adjacent}); 
			}
			
			aux = aux.next; 
			
		}

		childPairs.poll();
		
		if(childPairs.peek() != null) { searchUndirected(start); }
		
	}
	
	// runs the undirected search.
	private static boolean runUndirected(Graph g) {
		
		setGraph(g);
		
		resetCycle();
		
		for(int i = 0; i < 1; i++) {
			
			if(cycleFound()) { break; }
			
			childPairs.offer(new Integer[] {i, i});
			
			searchUndirected(i);
			
			childPairs.clear();
			
			path.clear();
				
		}
		
		
		return cycleFound();
		
	}
	
	// public entry point.
	public static boolean hasCycleUndirected(Graph g) { return runUndirected(g); }
	
	
}
