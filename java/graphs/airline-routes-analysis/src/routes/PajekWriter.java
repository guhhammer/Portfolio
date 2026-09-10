package routes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PajekWriter {

	private static Graph g;  // graph.
	
	private static String path; // output path.
	
	// sets the output path.
	private static void setPath(String name) {	
		path = "outputs/"+name+".pajek";
	}
	
	// sets the graph.
	private static void setGraph(Graph gr) { g = gr;}
	
	// writes the graph in Pajek format.
	private static void writePajek(boolean directed) throws FileNotFoundException {
		
		File file = new File(path);
		
		PrintWriter writer = new PrintWriter(file);
		
		writer.println("*Vertices   "+g.size);
		
		for(int i = 0; i < g.size; i++) {
			
			writer.println(i+" \""+g.vertices[i].getName()+"\"");
			
		}
		
		if(directed) { writer.println("*Arcs"); }
		else {writer.println("*Edges"); }
		
		for(int i = 0; i < g.size; i++) {
			
			SparseMatrix.Node aux = g.matrix.lists[i].first;
			
			while(aux != null) {
				
				writer.println(i+" "+aux.adjacent+" "+aux.value);
				
				aux = aux.next;
			
			}
			
		}
		
		writer.close();	
		
	}
	
	// public entry point.
	public static void writePajek(Graph g, String name, boolean directed) throws FileNotFoundException {
		
		setPath(name);
		
		setGraph(g);
		
		writePajek(directed);
		
	}
	
}
