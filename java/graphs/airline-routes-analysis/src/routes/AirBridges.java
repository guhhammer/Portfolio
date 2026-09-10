package routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class AirBridges {

	private static ArrayList<Object[]> paths = new ArrayList<>(); // all paths found.
	
	private static Stack<Integer> path = new Stack<>(); // current path from i to j.

	private static Graph g; // graph.
	
	// sets the graph.
	private static void setGraph(Graph gr) { g = gr; }
	
	// finds every path between i and j (directed or undirected graph).
	private static void find(int f, int distance, int limit, boolean show, 
									boolean switchConnections, String[] connections) {
		
		if(distinctPaths(paths).size() == limit) { return; }
		
		if(path.peek() != f) { 
			
			SparseMatrix.Node aux = g.matrix.lists[path.peek()].first;
			boolean jump = false;
			while(aux != null) {
				
				if(!path.contains(aux.adjacent)) {
					
					path.push(aux.adjacent);
					
					if(path.size() == distance+1 && path.peek() != f) {
						path.pop(); jump = true;
					}
					
					
					if(show) {System.out.println(Arrays.deepToString(path.toArray()));}
					
					if(!jump) { find(f, distance, limit, show, switchConnections, connections); }
					
					jump = false;
					
				}
				
				aux = aux.next;
		
			}
			
			if(aux == null) { path.pop(); if(show) {System.out.println(Arrays.deepToString(path.toArray()));}}
			
		}
		else {
			paths.add(path.toArray());
			
			if(switchConnections) { paths = withConnections(paths, connections); }
			else { paths = distinctPaths(paths); }
			
			path.pop();
			if(show) {System.out.println(Arrays.deepToString(path.toArray()));}
		}

	}
		
	// runs the run in order.
	private static ArrayList<Object[]> step(Graph g, int i, int f, int distance, int limit, 
								boolean show, boolean switchConnections, String[] connections) {
			
		paths.clear();
		path.clear();
		
		setGraph(g);
		
		if(i == f) { return new ArrayList<>(); }
		
		path.push(i);
		
		find(f, distance, limit, show, switchConnections, connections); 
		
		return paths;
		
	}
	
	// formats a path.
	private static String describe(Object[] aux) {
		
		String route = "\tP =>\t  ";
		for(int i = 0; i < aux.length; i++) {
			
			route += g.vertices[(int)aux[i]].getName();
		
			if(i != aux.length-1) { route += " -> "; }
			
		}
		
		return route+"\t=> D."; 
		
	}
	
	// prints up to `count` paths.
	private static void printPaths(ArrayList<Object[]> aux, int count) {
		
		int select = (count <= aux.size()) ? count : aux.size();
		
		if(count == 0 || aux.size() == 0) { 
			
			System.out.println("\n\nNo air bridge found!\n\n"); return;
		
		}
	
		for(int i = 0; i < select; i++) { System.out.println(describe(aux.get(i))); }
		System.out.println("\n");
		
	}

	// whether the path contains j.
	private static boolean contains(Object[] aux, int j) {
		
		for(int i = 0; i < aux.length; i++) { if(j == (int) aux[i]) { return true; }} return false;
		
	}
	
	// removes duplicate paths.
	private static ArrayList<Object[]> distinctPaths(ArrayList<Object[]> aux){
		
		ArrayList<Object[]> ret = new ArrayList<>();
		
		boolean check;
		for(int x = 0; x < aux.size(); x++) {
			
			check = true;
			for(int y = 0; y < ret.size(); y++) {
				
				if(x != y) { if(Arrays.equals(aux.get(x), ret.get(y))) { check = false; } }
				
			}
			
			if(check) { ret.add(aux.get(x)); }
			
		}
		
		return ret;

	}
	
	// removes duplicates and keeps the paths that pass through every connection.
	private static ArrayList<Object[]> withConnections(ArrayList<Object[]> aux, String[] connections){
		
		aux = distinctPaths(aux);
		
		ArrayList<Object[]> out = new ArrayList<>();
		
		boolean passed;
		for(int i = 0; i < aux.size(); i++) {
			passed = true;
			for(int j = 0; j < connections.length; j++) {
				passed = (contains(aux.get(i), g.findVertex(connections[j])));
				if(!passed) { break;}
			}
			if(passed) { out.add(aux.get(i)); }
		}
		return out;
		
	}

	// public entry point: prints the air bridges.
	public static void printAirBridges(Graph g, String start, String destination, int distance, 
												int maxPaths, boolean show) {
		
		setGraph(g);
		
		System.out.println(String.format("\nAir bridges of up to %d hops: \n",distance));
		
		printPaths(distinctPaths(step(g, g.findVertex(start), g.findVertex(destination),
					distance, maxPaths, show, false, new String[]{})), maxPaths);
		
	}
	
	// public entry point: prints the air bridges through the given connections.
	public static void printAirBridgesWithConnections(Graph g,String start, String[] connections, String destination, 
										int distance, int maxPaths, boolean show) {
		
		setGraph(g);
		System.out.println(String.format("\nAir bridges through "
									   + "( %s ) of up to %d hops: \n",
									   Arrays.toString(connections).replace("[","").replace("]", ""),
									   distance));
		
		printPaths(withConnections(step(g, g.findVertex(start), g.findVertex(destination),
				distance, maxPaths, show, true, connections), connections), maxPaths);
		
	}
	
}
