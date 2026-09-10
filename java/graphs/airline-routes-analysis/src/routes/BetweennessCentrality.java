package routes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BetweennessCentrality{
	
	private static ArrayList<Object[]> paths = new ArrayList<>(); // all paths found.
	
	private static Stack<Integer> path = new Stack<>(); // current path from i to j.

	private static Graph g; // graph.
	
	// sets the graph.
	private static void setGraph(Graph gr) { g = gr; }
	
	// finds every path between i and j (directed or undirected graph).
	private static void find(int f, boolean show) {
		
		if(path.peek() != f) {
			
			SparseMatrix.Node aux = g.matrix.lists[path.peek()].first;
			while(aux != null) {
				
				if(!path.contains(aux.adjacent)) {
					
					path.push(aux.adjacent);
					
					if(show) {System.out.println(Arrays.deepToString(path.toArray()));}
					
					find(f, show);
					
				}
				
				aux = aux.next;
			}
			if(aux == null) { path.pop(); if(show) {System.out.println(Arrays.deepToString(path.toArray()));}}
			
		}
		else {
			paths.add(path.toArray());
			path.pop();
			if(show) {System.out.println(Arrays.deepToString(path.toArray()));}
		}
	}
	
	// runs the run in order.
	private static ArrayList<Object[]> step(Graph g, int i, int f, boolean show) {
			
		paths.clear();
		path.clear();
		
		setGraph(g);
		
		if(i == f) { return new ArrayList<>(); }
		
		path.push(i);
		
		find(f, show);
		
		return paths;
		
	}
	
	// keeps only the shortest (geodesic) paths.
	private static ArrayList<Object[]> geodesics(ArrayList<Object[]> aux){
		
		ArrayList<Object[]> out = new ArrayList<>();
		
		if(aux != null) {
		
			ArrayList<Integer> sizes = new ArrayList<>();
			
			for(Object[] a : aux) { sizes.add(a.length); }
			
			int smallest = 999999999;
			for(Integer i : sizes) { if(i < smallest) { smallest = i; } }
			
			for(Object[] a : aux) { if(a.length == smallest) { out.add(a); } }
		
		}
		
		return out;
		
	}
	
	// fraction of the geodesics that pass through n.
	public static float betweennessShare(int n, ArrayList<Object[]> aux) {
		
		float appearances = 0.0f, size = (float) aux.size();
		
		for(Object[] a : aux) {	
			
			if(a.length < 3) { break; }
			
			for(int i = 1; i <= a.length-1; i++) { if( (int) a[i] == n) { appearances++;} }
			
		}
		
		return appearances/size;
	
	}
	
	// betweenness centrality of one vertex.
	public static float betweennessOf(Graph g, int vertex, boolean showStack) {
		
		// betweenness centrality.
		float sum = 0.0f, temp;
		String str = "";
		for(int i = 0; i < g.size; i++) {
			
			for(int j = 0; j < g.size; j++) {
				
				if(i != j) {
					
					temp = betweennessShare(vertex, geodesics(step(g, i, j, showStack)));
					str = temp+"";
					
					sum += (str.matches("[-+]?[0-9]*\\.?[0-9]+")) ? temp : 0.0f;
				
				}
				
			}
			
		}
		
		sum = (float) sum/2;
		
		float ci = sum/((g.size-1)*(g.size-2));
		
		return (g.directed) ? ci : 2*ci;
		
	}
	
	// prints the betweenness centrality of every vertex.
	public static void printBetweenness(Graph g, boolean showStacks) {
		
		System.out.println("\n\nBetweenness centrality:\n");
		for(int i = 0; i < g.size; i++) {
			
			System.out.print(String.format("betweenness of vertex %s:   %f\n",
							                       i, betweennessOf(g, i, showStacks)));
			
		}
		System.out.println("\n\n");
		
	}
	
}