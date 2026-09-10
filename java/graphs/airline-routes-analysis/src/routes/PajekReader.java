package routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PajekReader {
	
	private static Graph g; // Graph g.
	
	// reads a .pajek file.
	private static void read(File f) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(f);
		
		
		String numVertices = "";
		boolean directed = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Vertices")) {  numVertices = s.replace("*Vertices", "").trim(); }
			if(s.startsWith("*Arcs")) { directed = true; }
			if(s.startsWith("*Edges")) { directed = false; }
			
		} // vertex count and directedness.
		
		sc.close();
		sc = new Scanner(f);
		
		g = new Graph(Integer.parseInt(numVertices), directed); // create the graph.
		
		ArrayList<String[]> names = new ArrayList<>();
		boolean jump = false;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Vertices")) { jump = true; }
			if(s.startsWith("*Arcs") || s.startsWith("*Edges")) { break; }
						
			if(!jump) {
		
				names.add(s.replaceAll("\\s{2,}", " ").split(" "));			
	
			}
			
			jump = false;
			
		} // vertex labels.
		
		for(String[] n : names) {
			g.vertices[Integer.parseInt(n[0])].setName(n[1].replaceAll("\"", ""));
		}  // label the vertices.
		
		sc.close();
		sc = new Scanner(f);
		
		ArrayList<String[]> edges = new ArrayList<>();
		boolean pass = true;
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			
			if(s.startsWith("*Arcs") || s.startsWith("*Edges")) {   pass = false; s = sc.nextLine(); }
						
			if(!pass) {
		
				edges.add(s.replaceAll("\\s{2,}", " ").split(" "));
				
			}
			
		}  // edges.
		
		for(String[] a : edges) {
			
			g.addEdge(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
		
		}  // add the edges.
		
		sc.close();
		
	}
	
	// reads the file when it exists.
	private static Graph buildFrom(String name) {
		
		if(name.endsWith(".pajek")) {
			
			File file = new File(name);
		
			if(file.isFile()) {
				
				try{ read(file); }catch(Exception e) { e.getSuppressed(); }
			
				return g;
		
			}
			else {
				
				System.out.print("\nNote: file not found; the graph will be null.\n\n");
				
				return null;
			}
			
		}
		
		System.out.print("\nNote: not a .pajek file; the graph will be null.\n\n");
		
		return null;
	
	}
	
	// public entry point.
	public static Graph read(String name) {
		
		if(new File("outputs/"+name).exists()) {
			return buildFrom(("outputs/"+name));
		}
		else {
			return buildFrom(name);
		}
		
	}

	 
}
